## api-server

一个api工具集 ，还在开发中...









---



## Img-图片模块





### 已实现API



**获取随机图片 - 重定向**

```
GET: /img/url
```

- 获取`url`库中数据随机一个图片，进行**重定向**。
- 请求参数：`不必要`
  - `bili=` 默认为1，可选：`bili=1`、`bili=2`、`bili=3`
  - 不加请求参数为默认 `1`
    - `bili=1` ：
    - `bili=2` ：竖图
    - `bili=3` ：似正方形
- 示例1：[http://api.ayaka.icu/img/url](http://api.ayaka.icu/img/url) 获取数据一张横图图片
- 示例2：[http://api.ayaka.icu/img/url?bili=2](http://api.ayaka.icu/img/url?bili=2) 获取数据一张横图图片

<br> <hr> <br>





**获取随机图片 - 响应体**

```
GET: /img/url.io
```

- 获取`url`库中数据随机一个图片，进行**响应体返回**。
- 请求参数：`不必要`
  - `bili=` 默认为1，可选：`bili=1`、`bili=2`、`bili=3`
  - 不加请求参数为默认 `1`
    - `bili=1` ：
    - `bili=2` ：竖图
    - `bili=3` ：似正方形
- 示例1：[http://api.ayaka.icu/img/url.io](http://api.ayaka.icu/img/url.io) 获取数据一张横图图片
- 示例2：[http://api.ayaka.icu/img/url.io?bili=2](http://api.ayaka.icu/img/url.io?bili=2) 获取数据一张横图图片
- 注意：性能`GET: /img/url`差

<br> <hr> <br>







**获取随机图片 - JSON数据**

```
GET: /img/url/json
```

- 获取`url`库中数据随机一个图片信息，**JSON返回**
- 请求参数：无
- 示例1：[http://api.ayaka.icu/img/url/json](http://api.ayaka.icu/img/url/json) 获取数据一张横图图片

<br> <hr> <br>





**获取随机图片 - 响应体 - 服务器**

```
GET: /img/file.io
```

- 获取`file`库中数据随机一个图片，进行**响应体返回**。
- 请求参数：`不必要`
  - `bili=` 默认为1，可选：`bili=1`、`bili=2`、`bili=3`
  - 不加请求参数为默认 `1`
    - `bili=1` ：
    - `bili=2` ：竖图
    - `bili=3` ：似正方形
- 示例1：[https://api.ayaka.icu/img/file.io](https://api.ayaka.icu/img/file.io) 获取数据一张横图图片
- 示例2：[https://api.ayaka.icu/img/file.io?bili=2](https://api.ayaka.icu/img/file.io?bili=2) 获取数据一张横图图片
- 注意：性能`GET: /img/url.io` 好

<br> <hr> <br>


**获取给定诺干URL随机图片 - 重定向**

```
GET: /img/tool/urls?urls=xxx@@xxx
```

- 获取给定诺干URL图片地址，随机其中一个进行**重定向返回**。
- 请求参数：`必要`
  - `urls=路径1@@路径2@@更多` 
  - 每个路径以**`@@`**相隔
- 示例1：[https://api.ayaka.icu//img/tool/urls?urls=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg](https://api.ayaka.icu/img/tool/urls?urls=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg) 
- 注意：性能`GET: /img/url.io` 好


<br> <hr> <br>


**获取给定诺干URL随机图片 - 响应体返回**

```
GET: /img/tool/urls.io?urls=xxx@@xxx
```

- 获取给定诺干URL图片地址，随机其中一个进行**响应体返回**。
- 请求参数：`必要`
  - `urls=路径1@@路径2@@更多` 
  - 每个路径以**`@@`**相隔
- 示例1：[https://api.ayaka.icu//img/tool/urls.io?urls=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg](https://api.ayaka.icu/img/tool/urls.io?urls=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg) 
- 注意：
  - 性能`GET: /img/tool/urls?urls=xxx@@xxx` 差
  - **非必要请勿使用！**


<br> <hr> <br>








---





### 后台管理API



TODO: 以实现，文档改天写




---



### 部署&实现原理







**库表设计：**

从网络中获取url

```mysql
CREATE TABLE `tb_img` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `url` varchar(128) NOT NULL UNIQUE COMMENT '图片路径',
  `size` double(5,3) NOT NULL COMMENT '图片大小: 单位MB 最大  double(5,2)',
  `name` varchar(32) NOT NULL COMMENT '图片名称',
  `type` varchar(4) NOT NULL COMMENT '图片类型 jpg/png/...',
  `scale` int(2) NOT NULL COMMENT '图片比例 1:横屏 2:竖屏 3:类似正方形',
  `width` int(5) NOT NULL COMMENT '图片宽度',
  `height` int(5) NOT NULL COMMENT '图片长度',
  `json` varchar(168) NULL COMMENT '图片JSON格式，key：url+size+width+height+type',
  `time` timestamp NULL DEFAULT NULL COMMENT '图片更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

从本机路径中获取url

```mysql
CREATE TABLE `tb_img_file` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `path` varchar(128) NOT NULL UNIQUE COMMENT '图片文件路径',
  `size` double(5,3) NOT NULL COMMENT '图片大小: 单位MB 最大  double(5,2)',
  `name` varchar(32) NOT NULL COMMENT '图片名称',
  `type` varchar(4) NOT NULL COMMENT '图片类型 jpg/png/...',
  `scale` int(2) NOT NULL COMMENT '图片比例 1:横屏 2:竖屏 3:类似正方形',
  `width` int(5) NOT NULL COMMENT '图片宽度',
  `height` int(5) NOT NULL COMMENT '图片长度',
  `json` varchar(168) NULL COMMENT '图片JSON格式，key：url+size+width+height+type',
  `time` timestamp NULL DEFAULT NULL COMMENT '图片更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```



TODO: 以后写
