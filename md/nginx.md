# Nginx总结
## 安装
### windows版安装
官网下载zip解压即可，相关配置和指令，看后续描述。
### linux版安装
linux下安装nginx，要具备依赖环境：pcre、zlib、openssl。安装依赖时，如需要修改默认安装路径
则需要添加'--prefix'参数，然后安装nginx时也需要指定各个依赖的configure所在的目录。
这里以非默认路径安装为例：
- 安装PCRE库  
```shell script
tar -zxvf pcre-8.39.tar.gz
cd pcre-8.39
./configure --prefix=/app/appopt/nginx/env/pcre-8.39 
make
make install
```

- 安装zlib库  
```shell script
tar -zxvf zlib-1.2.11.tar.gz
cd zlib-1.2.11
./configure --prefix=/app/appopt/nginx/env/zlib-1.2.11
make
make install
```

- 安装openssl  
```shell script
tar -zxvf openssl-1.0.1t.tar.gz
cd openssl-1.0.1t
./config --prefix=/app/appopt/nginx/env/openssl-1.0.1t
make depend
make
make install
```
注意：安装过程提示：*** Because of configuration changes, you MUST do the following before*** building: make depend。
所以在make之前，先进行make depend。

- 安装nginx  
由于依赖的环境是在默认路径下安装的，因此安装nginx时需要指定它所依赖的环境的路径
```shell script
tar -zxvf nginx-1.14.0.tar.gz
cd nginx-1.14.0
# 创建日志目录：mkdir logs
./configure --prefix=/app/appopt/nginx/nginx-1.14.0 --conf-path=/app/appopt/nginx/nginx-1.14.0/nginx.conf --with-http_stub_status_module --with-http_ssl_module --with-file-aio --with-http_realip_module --with-stream --with-pcre=/app/appopt/nginx/pcre-8.39 --with-zlib=/app/appopt/nginx/zlib-1.2.11 --with-openssl=/app/appopt/nginx/openssl-1.0.1t
make 
make install
```
注意：  
1. 执行configure指令报错，是因为找不到依赖的环境opensll、zlib、pcre等，需要在configure后面指定相关环境路径如：--with-pcre=<path>。
报错信息示例：
```shell script
./configure: error: the HTTP rewrite module requires the PCRE library.
You can either disable the module by using --without-http_rewrite_module
option, or install the PCRE library into the system, or build the PCRE library
statically from the source with nginx by using --with-pcre=<path> option.
```
2. 执行make && make install指令时报错，提示报找不到文件，其实是因为，安装nginx时会去找依赖环境的configure文件，所以nginx的configure指令要指定依赖环境的解压时包含其configure的路径。
报错信息示例：
```shell script
make -f objs/Makefile
make[1]: Entering directory `/app/appopt/nginx/nginx'
cd /app/appopt/nginx/env/pcre-8.39 \
&& if [ -f Makefile ]; then make distclean; fi \
&& CC="cc" CFLAGS="-O2 -fomit-frame-pointer -pipe " \
./configure --disable-shared 
/bin/sh: line 2: ./configure: 没有那个文件或目录
make[1]: *** [/app/appopt/nginx/env/pcre-8.39/Makefile] 错误 127
make[1]: Leaving directory `/app/appopt/nginx/nginx'
make: *** [build] 错误 2
```
- 附：检查环境是否安装，如检查pcre:  
 `rpm -qa | grep 'pcre'`
## nginx常用指令
```shell script
# 进入nginx指令所在目录
cd /app/appopt/nginx-1.14.0/sbin

# 启动，不指定配置文件，使用默认配置文件
nginx

# 启动，指定配置文件
nginx -c conf/nginx.conf

# 检查配置文件是否生效
nginx -t

# 重启nginx
nginx -s reload

# 停止nginx
nginx -s stop

# 优雅停止nginx
# 有连接时会等连接请求完成再杀死worker进程  
nginx -s quit

# 优雅重启，并重新载入配置文件nginx.conf
nginx -s reload
# 重新打开日志文件，一般用于切割日志
nginx -s reopen

# 查看版本 
nginx -v

# 详细版本信息，包括编译参数
nginx -V

# 帮助 
nginx -h

```
## nginx配置

