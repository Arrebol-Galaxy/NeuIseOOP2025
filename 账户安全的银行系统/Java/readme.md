这是Java版本的代码，与Cpp不同的是直接使用了Java标准库的东西，不需要多安装依赖了直接放同一个文件夹里面运行即可（VSCode的Java拓展支持的功能，不确定别的软件有没有，这个挺方便的）

## 执行结果

```bash
arrebol-galaxy@ArrebolMacBook-Pro Bank % cd /Users/arrebol-galaxy/Documents/2025OOP/Bank ; /usr/bin/env /Library/Java/JavaVirtualMachines/
jdk-17.jdk/Contents/Home/bin/java -XX:+ShowCodeDetailsInExceptionMessages -cp /Users/arrebol-galaxy/Library/Application\ Support/Code/User
/workspaceStorage/f27382bab8a604577e926f568e293b7c/redhat.java/jdt_ws/Bank_5705a2ea/bin Main 
初始账户 A: 5000, B: 5000
正在启动 10 个用户进行并发测试...
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4856},"to_account":{"id":"acc_1002","new_balance":5213}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":5046}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4768}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4859}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4825}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":5004},"to_account":{"id":"acc_1002","new_balance":5042}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4926}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":5012},"to_account":{"id":"acc_1002","new_balance":5057}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4764}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":5027}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4951},"to_account":{"id":"acc_1002","new_balance":5118}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4992},"to_account":{"id":"acc_1002","new_balance":5077}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4716},"to_account":{"id":"acc_1002","new_balance":5261}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4740}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4781}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4742},"to_account":{"id":"acc_1002","new_balance":5300}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4793}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4780}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4743}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4657},"to_account":{"id":"acc_1002","new_balance":5386}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4633}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4569},"to_account":{"id":"acc_1002","new_balance":5450}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4548},"to_account":{"id":"acc_1002","new_balance":5471}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4615}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4586}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4562}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4480}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4452},"to_account":{"id":"acc_1002","new_balance":5499}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4436},"to_account":{"id":"acc_1002","new_balance":5515}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4475}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4401}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4406},"to_account":{"id":"acc_1002","new_balance":5518}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4409}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4309},"to_account":{"id":"acc_1002","new_balance":5615}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4303}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4371}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4320},"to_account":{"id":"acc_1002","new_balance":5666}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4228}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4256},"to_account":{"id":"acc_1002","new_balance":5716}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4306}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4236}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4300}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4267}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4336}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4302},"to_account":{"id":"acc_1002","new_balance":5750}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":4266},"to_account":{"id":"acc_1002","new_balance":5786}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4184}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4090}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4133}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":4090}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3999},"to_account":{"id":"acc_1002","new_balance":5877}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3970}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3909}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3893}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3866},"to_account":{"id":"acc_1002","new_balance":5904}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3878}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3875},"to_account":{"id":"acc_1002","new_balance":5907}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3813}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3770}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3808}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3712}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3714}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3732}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3656},"to_account":{"id":"acc_1002","new_balance":5983}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3636},"to_account":{"id":"acc_1002","new_balance":6003}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3653}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3557},"to_account":{"id":"acc_1002","new_balance":6099}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3470},"to_account":{"id":"acc_1002","new_balance":6186}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3381}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3421}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3357},"to_account":{"id":"acc_1002","new_balance":6250}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3308},"to_account":{"id":"acc_1002","new_balance":6299}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3221}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3142},"to_account":{"id":"acc_1002","new_balance":6378}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3193},"to_account":{"id":"acc_1002","new_balance":6327}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3093},"to_account":{"id":"acc_1002","new_balance":6427}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3089},"to_account":{"id":"acc_1002","new_balance":6431}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3069}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":3047},"to_account":{"id":"acc_1002","new_balance":6453}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3008}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3062}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":3012}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2979},"to_account":{"id":"acc_1002","new_balance":6486}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2963}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2935},"to_account":{"id":"acc_1002","new_balance":6514}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2870}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2822},"to_account":{"id":"acc_1002","new_balance":6562}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2819}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2803},"to_account":{"id":"acc_1002","new_balance":6578}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2758},"to_account":{"id":"acc_1002","new_balance":6623}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2734}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2827}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2648},"to_account":{"id":"acc_1002","new_balance":6709}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2664}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2659},"to_account":{"id":"acc_1002","new_balance":6714}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2628},"to_account":{"id":"acc_1002","new_balance":6745}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2674},"to_account":{"id":"acc_1002","new_balance":6773}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2702}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2667}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2568}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2548}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2494}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2394},"to_account":{"id":"acc_1002","new_balance":6873}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2320}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2207}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2282}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":2169},"to_account":{"id":"acc_1002","new_balance":6911}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2086}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2017}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":2033}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1951}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1892},"to_account":{"id":"acc_1002","new_balance":6970}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1867}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1805},"to_account":{"id":"acc_1002","new_balance":7036}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1871}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1782},"to_account":{"id":"acc_1002","new_balance":7059}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1769},"to_account":{"id":"acc_1002","new_balance":7072}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1712},"to_account":{"id":"acc_1002","new_balance":7129}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1760},"to_account":{"id":"acc_1002","new_balance":7081}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1665},"to_account":{"id":"acc_1002","new_balance":7176}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1677}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1616},"to_account":{"id":"acc_1002","new_balance":7237}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1711}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1632}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1584},"to_account":{"id":"acc_1002","new_balance":7285}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1604}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1599}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1503}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1497},"to_account":{"id":"acc_1002","new_balance":7291}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1523}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1483},"to_account":{"id":"acc_1002","new_balance":7305}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1513},"to_account":{"id":"acc_1002","new_balance":7315}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1423}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1386}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1415}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1363},"to_account":{"id":"acc_1002","new_balance":7367}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1342},"to_account":{"id":"acc_1002","new_balance":7388}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1362}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1329},"to_account":{"id":"acc_1002","new_balance":7421}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1310},"to_account":{"id":"acc_1002","new_balance":7440}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1237}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1314}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1212}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1133},"to_account":{"id":"acc_1002","new_balance":7519}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1221}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1185}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1085},"to_account":{"id":"acc_1002","new_balance":7619}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1014},"to_account":{"id":"acc_1002","new_balance":7690}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":982},"to_account":{"id":"acc_1002","new_balance":7722}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":977},"to_account":{"id":"acc_1002","new_balance":7727}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":1070}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":1034},"to_account":{"id":"acc_1002","new_balance":7763}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":979}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":993}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":974},"to_account":{"id":"acc_1002","new_balance":7782}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":884},"to_account":{"id":"acc_1002","new_balance":7872}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":851},"to_account":{"id":"acc_1002","new_balance":7905}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":899}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":873},"to_account":{"id":"acc_1002","new_balance":7931}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":825}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":852}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":804},"to_account":{"id":"acc_1002","new_balance":7979}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":800},"to_account":{"id":"acc_1002","new_balance":7983}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":756}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":722},"to_account":{"id":"acc_1002","new_balance":8017}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":666}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":692}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":592},"to_account":{"id":"acc_1002","new_balance":8117}}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":542}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":491}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":401},"to_account":{"id":"acc_1002","new_balance":8207}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":379},"to_account":{"id":"acc_1002","new_balance":8229}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":369},"to_account":{"id":"acc_1002","new_balance":8239}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":347},"to_account":{"id":"acc_1002","new_balance":8261}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":264},"to_account":{"id":"acc_1002","new_balance":8344}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":241},"to_account":{"id":"acc_1002","new_balance":8367}}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":153},"to_account":{"id":"acc_1002","new_balance":8455}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":186}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":144},"to_account":{"id":"acc_1002","new_balance":8518}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":207}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":51}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":98}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":10},"to_account":{"id":"acc_1002","new_balance":8606}}
线程 87: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 87: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 87: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 87: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 87: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
线程 87: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":9}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":42}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":76}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":66}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":107}
线程 79: Transfer 操作失败。状态码: 400, 响应体: {"status":"error", "message":"转账余额不足"}
Transfer 操作成功。响应: {"status":"success","from_account":{"id":"acc_1001","new_balance":42},"to_account":{"id":"acc_1002","new_balance":8630}}
Save 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":75}
Withdraw 操作成功。响应: {"status":"success", "account":"acc_1001", "new_balance":7}
所有用户线程已完成任务。
服务器已停止
```