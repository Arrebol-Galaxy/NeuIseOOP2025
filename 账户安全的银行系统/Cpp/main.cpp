#include <iostream>
#include <thread>
#include <vector>
#include <random>
#include <chrono>

#include "Server/Server.hpp"
#include "User/User.hpp"

constexpr int NUM_USERS = 10;
constexpr int TRANSACTIONS_PER_USER = 20;
const std::string HOST = "localhost";
constexpr int PORT = 8080;
const std::string ACCOUNT_ID_A = "acc_1001";
const std::string ACCOUNT_ID_B = "acc_1002";


void user_task(User& user) {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> opDist(0, 2); // 0: save, 1: withdraw, 2: transfer
    std::uniform_int_distribution<> amountDist(1, 100);

    for (int i = 0; i < TRANSACTIONS_PER_USER; ++i) {
        int operation = opDist(gen);
        int amountVal = amountDist(gen);
        Money::Money amount(amountVal);

        try {
            switch (operation) {
                case 0:
                    std::cout << "用户 " << std::this_thread::get_id() << " 尝试存入 " << amountVal << " 到账户 " << ACCOUNT_ID_A << std::endl;
                    user.Save(amount, ACCOUNT_ID_A);
                    break;
                case 1:
                    std::cout << "用户 " << std::this_thread::get_id() << " 尝试从账户 " << ACCOUNT_ID_A << " 取出 " << amountVal << std::endl;
                    user.Withdraw(amount, ACCOUNT_ID_A);
                    break;
                case 2:
                     std::cout << "用户 " << std::this_thread::get_id() << " 尝试从账户 " << ACCOUNT_ID_A << " 转账 " << amountVal << " 到账户 " << ACCOUNT_ID_B << std::endl;
                     user.Transfer(amount, ACCOUNT_ID_A, ACCOUNT_ID_B);
                    break;
            }
        } catch (const std::exception& e) {
            std::cerr << "用户 " << std::this_thread::get_id() << " 遇到异常: " << e.what() << std::endl;
        }

        std::this_thread::sleep_for(std::chrono::milliseconds(std::uniform_int_distribution<>(10, 100)(gen)));
    }
}

int main() {
    Server bankServer(HOST, PORT);
    std::thread serverThread([&]() {
        std::cout << "正在启动服务器..." << std::endl;
        bankServer.GetDatabase().CreateAccount(ACCOUNT_ID_A, 500);
        bankServer.GetDatabase().CreateAccount(ACCOUNT_ID_B, 500);
        bankServer.Start();
    });

    bankServer.WaitUntilReady();
    std::cout << "服务器正在运行。" << std::endl;

    std::vector<std::thread> userThreads;
    std::vector<User> users;
    users.reserve(NUM_USERS);

    for (int i = 0; i < NUM_USERS; ++i) {
        users.emplace_back(HOST, PORT);
    }

    std::cout << "正在启动 " << NUM_USERS << " 个用户线程..." << std::endl;
    for (int i = 0; i < NUM_USERS; ++i) {
        userThreads.emplace_back(user_task, std::ref(users[i]));
    }

    for (auto& t : userThreads) {
        t.join();
    }
    std::cout << "所有用户线程已完成任务。" << std::endl;

    bankServer.Stop();
    serverThread.join();

    std::cout << "服务器已停止。" << std::endl;
    std::cout << "模拟完成。请查看 'transaction_log.txt' 获取详情。" << std::endl;

    return 0;
}