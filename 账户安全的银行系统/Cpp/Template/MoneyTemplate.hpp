//
// Created by Arrebol Lee on 25-9-20.
//

#ifndef MONEYTEMPLATE_H
#define MONEYTEMPLATE_H

namespace Money {
    class Money {
    private:
        int money = 0;
    public:
        explicit Money(const int amount = 0) {
            this->money = amount;
        }

        [[nodiscard]] int GetBalance() const {
            return money;
        }

        void Add(const int& modification) {
            // modification的正负代表存入取出
            money += modification;
        }
    };
}

#endif //MONEYTEMPLATE_H