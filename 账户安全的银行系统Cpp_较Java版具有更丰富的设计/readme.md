# Cpp版本的银行账户管理
## 概述

***项目文档将由文档生成LLM生成***

### 项目简介

银行账户管理系统是一个基于C++开发的多线程银行模拟系统，实现了基本的银行操作功能，包括存款、取款、转账等。该系统采用客户端-服务器架构，支持多用户并发操作，并具有完善的日志记录功能。

### 功能特性

#### 核心功能
- **账户管理**：创建和管理银行账户
- **存款操作**：向账户存入资金
- **取款操作**：从账户取出资金，支持余额不足时的等待机制
- **转账操作**：在账户间进行资金转移
- **并发处理**：支持多用户同时进行银行操作
- **日志记录**：详细记录所有交易操作，便于审计和追踪

#### 技术特点
- **多线程安全**：使用互斥锁和条件变量确保并发操作的安全性
- **RESTful API**：基于HTTP协议的接口设计，便于系统集成
- **实时日志**：记录所有交易操作的时间戳和详细信息
- **错误处理**：完善的异常处理机制，确保系统稳定性

### 系统架构

```
+----------------+     HTTP Requests     +----------------+
|    用户客户端  | <-------------------> |   服务器端     |
+----------------+                       +----------------+
                                          |      |
                                          |      |
                                   +------v--+   +--v------+
                                   | 数据库  |   | 日志系统 |
                                   +---------+   +---------+
```

#### 主要组件

1. **服务器 (Server)**
   - 监听HTTP请求并处理银行操作
   - 管理数据库和日志系统
   - 提供RESTful API接口

2. **用户客户端 (User)**
   - 模拟银行用户操作
   - 发送HTTP请求到服务器执行银行操作

3. **数据库 (Database)**
   - 管理账户信息和余额
   - 确保数据一致性与并发安全

4. **日志系统 (Log)**
   - 记录所有交易操作
   - 提供时间戳和详细操作信息

### 技术栈

- **编程语言**：C++20
- **构建系统**：CMake
- **网络库**：cpp-httplib
- **JSON处理**：nlohmann/json
- **并发控制**：STL线程库 (mutex, condition_variable)

### 快速开始

#### 环境要求
- C++20 编译器
- CMake 3.29 或更高版本
- vcpkg 包管理器

#### 构建步骤
```bash
## 克隆项目
git clone <项目地址> // 这会儿还不知道放哪儿好所以没写
cd BankAccountManager

## 使用 vcpkg 安装依赖
vcpkg install cpp-httplib nlohmann-json openssl

## 创建构建目录
mkdir build && cd build

## 配置项目
cmake .. -DCMAKE_TOOLCHAIN_FILE=<vcpkg_path>/scripts/buildsystems/vcpkg.cmake

## 编译项目
make

## 运行程序
./BankAccountManager
```

#### 运行说明
程序运行时会启动一个本地服务器，默认监听端口为8080。系统会创建两个初始账户并启动多个用户线程进行随机的银行操作（存款、取款、转账）。

### API 接口

- **POST /deposit** - 存款接口
- **POST /withdraw** - 取款接口
- **POST /transfer** - 转账接口

### 日志格式

所有交易操作都会记录在 `transaction_log.txt` 文件中，格式如下：
```
[YYYY-MM-DD HH:MM:SS.MS] 操作类型: 详细信息
```

### 项目结构

```
BankAccountManager/
├── Database/           ## 数据库相关代码
├── Log/                ## 日志系统代码
├── Server/             ## 服务器端代码
├── Template/           ## 数据模板定义
├── ThirdParty/         ## 第三方库
├── Untils/             ## 工具函数
├── User/               ## 用户客户端代码
├── main.cpp            ## 程序入口点
└── CMakeLists.txt      ## 构建配置文件
```

### 开发规范

- 遵循C++20标准
- 使用驼峰命名法（PascalCase for 类/函数，camelCase for 变量）
- 所有运行时输出使用中文
- 确保线程安全



## 编译

在macOS 26上经clang编译通过，依赖管理主要使用CLion+Vcpkg，使用的第三方库有`nlohmann_json`和`httplib_cpp`和`OpenSSL`. 

使用基于CMake的工具链应当是可以通过的. 我直接用的CLion的编译功能，没有独立使用过`make`进行编译. 

## 某一次的运行结果

```bash
/Users/arrebol-galaxy/Documents/想法/BankAccountManager/cmake-build-debug/BankAccountManager
服务器正在运行。
正在启动服务器...
正在启动 10 个用户线程...
用户 用户 0x16d4b3000 尝试存入 61 到账户 acc_1001
0x16d53f000 尝试从账户 acc_1001 取出 99
用户 0x16d5cb000 尝试从账户 acc_1001 取出 11
用户 0x16d657000 尝试从账户 acc_1001 取出 54
用户 0x16d6e3000 尝试从账户 acc_1001 转账 42 到账户 acc_1002
用户 用户 0x16d7fb000 尝试从账户 acc_1001 取出 72
用户 0x16d913000 尝试存入 54 到账户 acc_1001
用户 0x16d887000 尝试从账户 acc_1001 取出 32
0x16d76f000 尝试从账户 acc_1001 取出 8用户 0x16d99f000 尝试从账户 acc_1001 转账 58 到账户 acc_1002

Save 操作成功。响应: {"account":"acc_1001","new_balance":482,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":317},"status":"success","to_account":{"id":"acc_1002","new_balance":600}}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":428,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":186,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":417,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":285,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":239,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":428,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":359},"status":"success","to_account":{"id":"acc_1002","new_balance":558}}
Save 操作成功。响应: {"account":"acc_1001","new_balance":247,"status":"success"}
用户 0x16d5cb000 尝试从账户 acc_1001 取出 23
用户 0x16d887000 尝试存入 55 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":216,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":271,"status":"success"}
用户 0x16d4b3000 尝试存入 4 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":275,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 21
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":254,"status":"success"}
用户 0x16d657000 尝试从账户 acc_1001 转账 97 到账户 acc_1002
用户 0x16d6e3000 尝试从账户 acc_1001 转账 49 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":157},"status":"success","to_account":{"id":"acc_1002","new_balance":697}}
用户 0x16d913000 尝试从账户 acc_1001 转账 100 到账户 acc_1002
用户 0x16d99f000 尝试从账户 acc_1001 取出 78
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":108},"status":"success","to_account":{"id":"acc_1002","new_balance":746}}
用户 0x16d53f000 尝试从账户 acc_1001 取出 20
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":30,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":10,"status":"success"}
用户 0x16d6e3000 尝试从账户 acc_1001 取出 10
线程 0x16d913000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":0,"status":"success"}
用户 0x16d76f000 尝试存入 75 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":75,"status":"success"}
用户 0x16d7fb000 尝试存入 3 到账户 acc_1001
用户 0x16d887000 尝试存入 73 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":78,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":151,"status":"success"}
用户 0x16d913000 尝试存入 69 到账户 acc_1001
用户 0x16d6e3000 尝试从账户 acc_1001 转账 93 到账户 acc_1002
Save 操作成功。响应: {"account":"acc_1001","new_balance":220,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":127},"status":"success","to_account":{"id":"acc_1002","new_balance":839}}
用户 0x16d4b3000 尝试从账户 acc_1001 转账 53 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":74},"status":"success","to_account":{"id":"acc_1002","new_balance":892}}
用户 0x16d887000 尝试存入 28 到账户 acc_1001
用户 0x16d5cb000 尝试存入 11 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":102,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":113,"status":"success"}
用户 0x16d53f000 尝试存入 40 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":153,"status":"success"}
用户 0x16d99f000 尝试从账户 acc_1001 取出 5
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":148,"status":"success"}
用户 0x16d7fb000 尝试存入 17 到账户 acc_1001
用户 0x16d657000 尝试从账户 acc_1001 取出 40
Save 操作成功。响应: {"account":"acc_1001","new_balance":165,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":125,"status":"success"}
用户 0x16d887000用户 0x16d6e3000 尝试从账户 acc_1001 取出  尝试存入 78 到账户 acc_100130

Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":173,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":203,"status":"success"}
用户 0x16d657000 尝试从账户 acc_1001 取出 62
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":111,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 89
用户 0x16d5cb000 尝试存入 62 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":22,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":84,"status":"success"}
用户 0x16d53f000 尝试存入 41 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":125,"status":"success"}
用户 0x16d913000 尝试从账户 acc_1001 取出 31
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":94,"status":"success"}
用户 0x16d7fb000 尝试存入 70 到账户 acc_1001
用户 0x16d4b3000 尝试从账户 acc_1001 转账 66 到账户 acc_1002
Save 操作成功。响应: {"account":"acc_1001","new_balance":164,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":98},"status":"success","to_account":{"id":"acc_1002","new_balance":958}}
用户 0x16d6e3000 尝试从账户 acc_1001 取出 13
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":85,"status":"success"}
用户 0x16d53f000 尝试存入 70 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":155,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 6
用户 0x16d99f000 尝试存入 46 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":149,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":195,"status":"success"}
用户 0x16d887000 尝试从账户 acc_1001 取出 40
用户 0x16d913000 尝试存入 52 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":155,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":207,"status":"success"}
用户 0x16d657000 尝试从账户 acc_1001 转账 29 到账户 acc_1002
用户 0x16d99f000 尝试存入 33 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":240,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":211},"status":"success","to_account":{"id":"acc_1002","new_balance":987}}
用户 0x16d887000 尝试存入 9 到账户 acc_1001
用户 0x16d7fb000 尝试从账户 acc_1001 取出 54
Save 操作成功。响应: {"account":"acc_1001","new_balance":220,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":166,"status":"success"}
用户 0x16d5cb000 尝试从账户 acc_1001 转账 33 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":133},"status":"success","to_account":{"id":"acc_1002","new_balance":1020}}
用户 0x16d4b3000 尝试从账户 acc_1001 取出 33
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":100,"status":"success"}
用户 0x16d6e3000 尝试从账户 acc_1001 取出 52
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":48,"status":"success"}
用户 0x16d913000 尝试存入 96 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":144,"status":"success"}
用户 0x16d99f000 尝试从账户 acc_1001 转账 25 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":119},"status":"success","to_account":{"id":"acc_1002","new_balance":1045}}
用户 0x16d657000 尝试存入 80 到账户 acc_1001
用户 0x16d53f000 尝试从账户 acc_1001 取出 21
Save 操作成功。响应: {"account":"acc_1001","new_balance":199,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":178,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 29
用户 0x16d887000 尝试存入 23 到账户 acc_1001
用户 0x16d7fb000 尝试从账户 acc_1001 取出 83
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":149,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":172,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":89,"status":"success"}
用户 0x16d99f000用户 0x16d6e3000 尝试从账户 acc_1001 转账 20 到账户 acc_1002
 尝试从账户 acc_1001 转账 52 到账户 acc_1002
用户 0x16d913000 尝试从账户 acc_1001 取出 45
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":44,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":24},"status":"success","to_account":{"id":"acc_1002","new_balance":1065}}
线程 0x16d99f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d887000 尝试存入 84 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":108,"status":"success"}
用户 0x16d5cb000 尝试从账户 acc_1001 转账 62 到账户 acc_1002
用户 0x16d657000 尝试从账户 acc_1001 取出 52
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":46},"status":"success","to_account":{"id":"acc_1002","new_balance":1127}}
用户 0x16d4b3000 尝试存入 75 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":69,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":121,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 28
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":41,"status":"success"}
用户 0x16d53f000 尝试从账户 acc_1001 取出 93
用户 0x16d657000 尝试存入 84 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":125,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":32,"status":"success"}
用户 0x16d7fb000 尝试从账户 acc_1001 转账 43 到账户 acc_1002
线程 0x16d7fb000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 用户 0x16d9130000x16d6e3000 尝试从账户 acc_1001 取出 33
 尝试存入 2 到账户 acc_1001
用户 0x16d7fb000 尝试从账户 acc_1001 转账 90 到账户 acc_1002
Save 操作成功。响应: {"account":"acc_1001","new_balance":34,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":1,"status":"success"}
用户 0x16d99f000 尝试从账户 acc_1001 转账 21 到账户 acc_1002
用户 0x16d76f000 尝试从账户 acc_1001 转账 88 到账户 acc_1002
用户 0x16d887000 尝试从账户 acc_1001 转账 74 到账户 acc_1002
用户 0x16d5cb000 尝试存入 91 到账户 acc_1001
用户 0x16d4b3000 尝试存入 72 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":164,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":164,"status":"success"}
线程 0x16d7fb000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d99f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d76f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d887000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d99f000 尝试存入 95 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":259,"status":"success"}
用户 0x16d6e3000 尝试从账户 acc_1001 取出 84
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":175,"status":"success"}
用户 0x16d7fb000 尝试存入 53 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":228,"status":"success"}
用户 0x16d887000 尝试从账户 acc_1001 取出 90
用户 0x16d913000 尝试存入 48 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":138,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":186,"status":"success"}
用户 0x16d4b3000 尝试从账户 acc_1001 取出 70
用户 0x16d53f000 尝试从账户 acc_1001 转账 43 到账户 acc_1002
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":116,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":73},"status":"success","to_account":{"id":"acc_1002","new_balance":1170}}
用户 0x16d657000 尝试存入 26 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":99,"status":"success"}
用户 0x16d887000 尝试存入 17 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":116,"status":"success"}
用户 0x16d4b3000 尝试从账户 acc_1001 转账 20 到账户 acc_1002
用户 0x16d6e3000 尝试从账户 acc_1001 转账 23 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":96},"status":"success","to_account":{"id":"acc_1002","new_balance":1190}}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":73},"status":"success","to_account":{"id":"acc_1002","new_balance":1213}}
用户 0x16d5cb000 尝试从账户 acc_1001 取出 7
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":66,"status":"success"}
用户 0x16d913000 尝试从账户 acc_1001 取出 89
用户 0x16d7fb000 尝试存入 84 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":150,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":61,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 30
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":31,"status":"success"}
用户 0x16d913000 尝试存入 39 到账户 acc_1001
用户 0x16d657000 尝试存入 77 到账户 acc_1001
用户 0x16d887000 尝试存入 50 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":70,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":197,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":147,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 63
用户 0x16d99f000 尝试从账户 acc_1001 转账 2 到账户 acc_1002
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":134,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":132},"status":"success","to_account":{"id":"acc_1002","new_balance":1215}}
用户 0x16d7fb000 尝试存入 41 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":173,"status":"success"}
用户 0x16d5cb000 尝试存入 31 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":204,"status":"success"}
用户 0x16d53f000 尝试从账户 acc_1001 取出 32
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":172,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 91
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":81,"status":"success"}
用户 0x16d4b3000 尝试存入 32 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":113,"status":"success"}
用户 0x16d7fb000 尝试从账户 acc_1001 转账 12 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":101},"status":"success","to_account":{"id":"acc_1002","new_balance":1227}}
用户 0x16d6e3000 尝试存入 30 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":131,"status":"success"}
用户 0x16d5cb000 尝试从账户 acc_1001 转账 23 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":108},"status":"success","to_account":{"id":"acc_1002","new_balance":1250}}
用户 0x16d4b3000 尝试从账户 acc_1001 取出 10
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":98,"status":"success"}
用户 0x16d657000 尝试存入 29 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":127,"status":"success"}
用户 0x16d913000 尝试从账户 acc_1001 转账 87 到账户 acc_1002
用户 0x16d99f000 尝试从账户 acc_1001 取出 96
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":40},"status":"success","to_account":{"id":"acc_1002","new_balance":1337}}
用户 0x16d76f000 尝试存入 67 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":11,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":11,"status":"success"}
用户 0x16d7fb000 尝试存入 51 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":62,"status":"success"}
用户 0x16d6e3000 尝试存入 26 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":88,"status":"success"}
用户 0x16d887000 尝试从账户 acc_1001 转账 98 到账户 acc_1002
用户 0x16d53f000 尝试从账户 acc_1001 取出 80
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":8,"status":"success"}
用户 0x16d6e3000 尝试存入 49 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":57,"status":"success"}
用户 0x16d7fb000 尝试从账户 acc_1001 取出 69
线程 0x16d887000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d4b3000 尝试从账户 acc_1001 取出 41
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":16,"status":"success"}
用户 0x16d99f000 尝试从账户 acc_1001 转账 13 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":3},"status":"success","to_account":{"id":"acc_1002","new_balance":1350}}
用户 0x16d76f000 尝试存入 30 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":33,"status":"success"}
用户 0x16d887000 尝试存入 57 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":90,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":21,"status":"success"}
用户 0x16d5cb000 尝试从账户 acc_1001 取出 35
用户 0x16d7fb000 尝试从账户 acc_1001 转账 37 到账户 acc_1002
用户 用户 0x16d657000 尝试从账户 acc_1001 取出 53
0x16d887000 尝试从账户 acc_1001 转账 52 到账户 acc_1002
用户 0x16d53f000 尝试从账户 acc_1001 取出 96
线程 0x16d7fb000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d887000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d913000 尝试从账户 acc_1001 转账 18 到账户 acc_1002
用户 0x16d887000 尝试从账户 acc_1001 转账 31 到账户 acc_1002
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":3},"status":"success","to_account":{"id":"acc_1002","new_balance":1368}}
线程 0x16d887000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d6e3000 尝试从账户 acc_1001 取出 48
用户 0x16d4b3000 尝试从账户 acc_1001 转账 96 到账户 acc_1002
用户 0x16d99f000 尝试从账户 acc_1001 取出 26
用户 0x16d76f000 尝试存入 83 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":25,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":25,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":51,"status":"success"}
线程 0x16d4b3000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d7fb000 尝试存入 91 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":20,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":20,"status":"success"}
用户 0x16d887000 尝试存入 79 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":99,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":51,"status":"success"}
用户 0x16d4b3000 尝试从账户 acc_1001 取出 55
用户 0x16d76f000 尝试从账户 acc_1001 转账 26 到账户 acc_1002
用户 0x16d53f000 尝试从账户 acc_1001 转账 62 到账户 acc_1002
用户 0x16d913000 尝试存入 53 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":51,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":51,"status":"success"}
Transfer 操作成功。响应: {"from_account":{"id":"acc_1001","new_balance":25},"status":"success","to_account":{"id":"acc_1002","new_balance":1394}}
用户 0x16d99f000 尝试从账户 acc_1001 转账 65 到账户 acc_1002
用户 0x16d887000 尝试从账户 acc_1001 转账 46 到账户 acc_1002
用户 0x16d6e3000 尝试从账户 acc_1001 取出 56
线程 0x16d53f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d99f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d887000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d5cb000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d5cb000 尝试从账户 acc_1001 转账 64 到账户 acc_1002
用户 0x16d5cb000 尝试从账户 acc_1001 取出 40
用户 0x16d7fb000 尝试存入 14 到账户 acc_1001
用户 0x16d887000 尝试从账户 acc_1001 取出 49
用户 0x16d53f000 尝试从账户 acc_1001 取出 58
Save 操作成功。响应: {"account":"acc_1001","new_balance":39,"status":"success"}
用户 0x16d913000 尝试从账户 acc_1001 取出 17
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":22,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 69
用户 0x16d657000 尝试从账户 acc_1001 转账 80 到账户 acc_1002
用户 0x16d99f000 尝试从账户 acc_1001 转账 23 到账户 acc_1002
用户 0x16d7fb000 尝试从账户 acc_1001 转账 87 到账户 acc_1002
线程 0x16d657000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d99f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d7fb000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d99f000 尝试从账户 acc_1001 转账 74 到账户 acc_1002
用户 0x16d657000 尝试存入 62 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":29,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":84,"status":"success"}
线程 0x16d99f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d7fb000 尝试从账户 acc_1001 转账 95 到账户 acc_1002
线程 0x16d7fb000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d7fb000 尝试存入 45 到账户 acc_1001
用户 0x16d913000 尝试存入 26 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":34,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":74,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":4,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":4,"status":"success"}
用户 用户 0x16d4b3000 尝试从账户 acc_1001 转账 34 到账户 acc_1002
0x16d657000 尝试存入 51 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":55,"status":"success"}
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":6,"status":"success"}
用户 0x16d99f000 尝试从账户 acc_1001 转账 78 到账户 acc_1002
线程 0x16d4b3000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d99f000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d4b3000 尝试从账户 acc_1001 取出 28
用户 0x16d913000 尝试从账户 acc_1001 转账 98 到账户 acc_1002
用户 0x16d6e3000 尝试从账户 acc_1001 转账 30 到账户 acc_1002
线程 0x16d913000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d6e3000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d913000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d913000 尝试从账户 acc_1001 转账 70 到账户 acc_1002
用户 0x16d5cb000 尝试存入 83 到账户 acc_1001
Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":20,"status":"success"}
Save 操作成功。响应: {"account":"acc_1001","new_balance":20,"status":"success"}
用户 0x16d657000 尝试从账户 acc_1001 转账 28 到账户 acc_1002
线程 0x16d657000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d6e3000 尝试存入 30 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":22,"status":"success"}
用户 0x16d99f000 尝试从账户 acc_1001 取出 19Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":22,"status":"success"}

Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":3,"status":"success"}
用户 0x16d76f000 尝试从账户 acc_1001 取出 89
用户 0x16d5cb000 尝试从账户 acc_1001 取出 77
用户 0x16d913000 尝试从账户 acc_1001 取出 13
用户 0x16d4b3000 尝试从账户 acc_1001 取出 26
用户 0x16d657000 尝试从账户 acc_1001 取出 35
用户 0x16d6e3000 尝试从账户 acc_1001 转账 49 到账户 acc_1002
用户 0x16d6e3000 尝试从账户 acc_1001 转账 50 到账户 acc_1002
线程 0x16d6e3000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 0x16d6e3000: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
用户 0x16d99f000 尝试存入 42 到账户 acc_1001
Save 操作成功。响应: {"account":"acc_1001","new_balance":32,"status":"success"}Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":32,"status":"success"}

Withdraw 操作成功。响应: {"account":"acc_1001","new_balance":6,"status":"success"}
用户 0x16d4b3000 尝试从账户 acc_1001 取出 17
用户 0x16d99f000 尝试从账户 acc_1001 取出 59
用户 0x16d913000 尝试从账户 acc_1001 取出 86

进程已结束，退出代码为 143 (interrupted by signal 15:SIGTERM)
```
