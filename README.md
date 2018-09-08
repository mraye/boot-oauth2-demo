# boot-oauth2-demo
基于spring boot 2.0.4.RELEASE和spring security oauth2

oauth2认证模式分为:

+ client_credentials(客户端)
+ password(密码)
+ implicit(隐式)
+ authorization code(授权码)


## client_credentials

**`client_credentials`是唯一一种不需要用户信息(Resource Owner)的认证方式**
POST请求:  

`http://localhost:8080/oauth/token?grant_type=client_credentials&client_id=client&client_secret=secret`


## password

`password`模式和`client_credentials`模式区别在于需要用户信息(Resource Owner)，但是没有用户登陆交互的操作，相反，`password`模式内部自己实现将向
使用用户信息向授权服务器(Authorization Server)进行认证，其他的和`client_credentials`模式一样

POST请求: 

`http://localhost:8080/oauth/token?grant_type=password&client_id=apple&client_secret=secret&username=admin&password=admin`




## implicit

`implicit`是需要用户登陆，在spring security中体现就是SecurityContext中存在已经认证后的`Authentication`,implicit`和`authorization code`唯一的区别就是不需要获取`code`

POST请求: 

`http://localhost:8080/oauth/authorize?response_type=token&client_id=banana&client_secret=secret&redirect_uri=http://baidu.com`



## authorization code

网页授权比较普遍一种模式，需要用户登陆交互授权，相比`implicit`模式，需要先获取`code`，然后根据`code`去获取`access_token`

1. 获取code:  

`http://localhost:8080/oauth/authorize?response_type=code&client_id=pair&redirect_uri=http://baidu.com`

2. 获取access_token

`http://localhost:8080/oauth/token?grant_type=authorization_code&code=N3EKeu&client_id=pair&client_secret=secret&redirect_uri=http://baidu.com`


![access_confirm](https://github.com/mraye/boot-oauth2-demo/raw/master/screenshots/login.png.png)

![access_confirm](https://github.com/mraye/boot-oauth2-demo/raw/master/screenshots/access_confirm.png)





