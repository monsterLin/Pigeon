![banner](http://oszh5svp5.bkt.clouddn.com/pigeon_banner_show.png)

## 飞鸽

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/monsterLin/Pigeon/master/LICENSE)
[![Build Status](https://travis-ci.org/monsterLin/Pigeon.svg?branch=master)](https://travis-ci.org/monsterLin/Pigeon)
[![GitHub stars](https://img.shields.io/github/stars/monsterLin/Pigeon.svg?style=social&label=Stars)](https://github.com/monsterLin/Pigeon/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/monsterLin/Pigeon.svg?style=social&label=Fork)](https://github.com/monsterLin/Pigeon/network)

### 前言

> 今天我们很高兴的给大家展示我们的这款项目，我们的项目旨在帮助父母了解互联网，加深子女与父母之间的情感为宗旨而设计的

> 秉承开源的精神，我们将程序分享给大家，希望大家一起维护，为开源精神多做分享

### 项目介绍

在互联网逐渐趋于智能化的今天，作为九零后的我们或许更加的青睐于互联网给我们带来的方便，但是，我们的父母等老一辈对互联网了解的却很少，据了解，
父母真正会使用互联网的占据40%左右，那么说，在我们使用互联网的前提下，我们应该也让父母会使用互联网

在今天，或许，我们无时无刻都抱着手机，我们很少的与父母聊天，或者由于工作的原因，我们很少陪在我们的父母身边，唯一的工具莫属于互联网

那么说这样一款适合父母的app是非常重要的

### 开发日志

- 2017年7月4日，项目开始搭建 :tada:
- 2017年7月6日，完成项目的基本框架的搭建 :art:
- 2017年7月11日，完成登陆，注册的功能 :sparkles:
- 2017年7月17日，更新分支策略 :fire:
- 2017年7月31日，完成记事本模块 :sparkles:
- 2017年8月4日，修复家庭部分的业务逻辑 :art: :bug:
- 2017年8月7日，升级UI，确定app主题格局 :lipstick:
- 2017年8月9日，1.0 雏形完成 :bookmark:

### 开发进度

> 更新时间：2017年08月09日

![pigeon](http://oszh5svp5.bkt.clouddn.com/pigeon_progress.png)

### 相关流程

#### 登陆逻辑分析

![loginLogic](http://oszh5svp5.bkt.clouddn.com/pigeon_login_anylise.png)

### 构建说明

目前程序使用`travis-ci`进行项目的持续化构建，但是由于本人的电脑的问题，无法完成release的线上的keystore的认证，只能通过travis-ci完成debug版本的构建

### 项目结构

```
    .
    └── pigeon
        ├── adapter
        │   ├── ChatMessageAdapter.java
        │   ├── FamilyNumberAdapter.java
        │   ├── GuidePageAdapter.java
        │   ├── StickerAdapter.java
        │   ├── ToolsAdapter.java
        │   ├── ViewPagerAdapter.java
        │   └── WeatherNumberAdapter.java
        ├── application
        │   └── PigeonApplication.java
        ├── base
        │   ├── BaseActivity.java
        │   ├── BaseApplication.java
        │   └── BaseFragment.java
        ├── bean
        │   ├── Advice.java
        │   ├── ChatMessage.java
        │   ├── Family.java
        │   ├── Result.java
        │   ├── Sticker.java
        │   ├── Tools.java
        │   └── User.java
        ├── common
        │   ├── AppManager.java
        │   └── CrashHandler.java
        ├── constant
        │   ├── AppConfig.java
        │   ├── BmobConfig.java
        │   ├── FamilyConfig.java
        │   └── TuLingConfig.java
        ├── MainActivity.java
        ├── ui
        │   ├── app
        │   │   ├── AboutActivity.java
        │   │   ├── AdviceActivity.java
        │   │   ├── ThanksActivity.java
        │   │   └── UpgradeExplainActivity.java
        │   ├── brower
        │   │   └── BrowerActivity.java
        │   ├── chat
        │   │   └── ChatActivity.java
        │   ├── family
        │   │   ├── CreateFamilyActivity.java
        │   │   ├── EditFamilyActivity.java
        │   │   ├── GuideFamilyActivity.java
        │   │   └── MyFamilyActivity.java
        │   ├── setting
        │   │   ├── NewStickerActivity.java
        │   │   └── SettingActivity.java
        │   ├── sticker
        │   │   ├── MyStickerActivity.java
        │   │   └── StickerActivity.java
        │   ├── user
        │   │   ├── ForgetPassActivity.java
        │   │   ├── LoginActivity.java
        │   │   ├── RegisterOrResetActivity.java
        │   │   ├── UpdateUserInfoActivity.java
        │   │   └── UserInfoActivity.java
        │   ├── weather
        │   │   └── WeatherActivity.java
        │   └── welcome
        │       ├── GuidePageActivity.java
        │       └── WelcomeActivtiy.java
        ├── utils
        │   ├── ChatRobotUtils.java
        │   ├── SHA1.java
        │   ├── SPUtils.java
        │   └── ToastUtils.java
        ├── vholder
        │   ├── FamilyNumberVHolder.java
        │   ├── StickerVHolder.java
        │   ├── ToolsVHolder.java
        │   └── WeatherNumberVHolder.java
        ├── view
        │   ├── HomeFragment.java
        │   ├── PersonFragment.java
        │   └── ToolsFragment.java
        └── widget
            ├── CountDownButtonHelper.java
            ├── LoadingDialog.java
            └── ProgressWebView.java

```

### 体验试用

#### 内测

> 内测声明：如需体验，请联系email : monster941025@gmail.com ，备注：Pigeon

- 1.0 ：[传送门](https://fir.im/Pigeon)

#### 公测

> 开发中


### 程序欣赏

### 鸣谢

- @ 西柚 ：提供设计支持
- @ [小莫](https://github.com/xiaomoinfo) ：提供CI支持

### 关于我

> 屌丝一枚，不甘现实
> 为梦想而奋斗的追梦人

@ Web：https://monsterlin.com
@ Email: monster941025#gmail.com （# 替换为 @）

### 相关资料

- [阿里巴巴Java开发手册v1.2.0](http://files.monsterlin.com/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8Cv1.2.0.pdf)
- [团队开发Git分支管理策略](https://helei112g.github.io/2017/04/14/%E5%9B%A2%E9%98%9F%E5%BC%80%E5%8F%91Git%E5%88%86%E6%94%AF%E7%AE%A1%E7%90%86%E7%AD%96%E7%95%A5/)


### [License](https://github.com/monsterLin/Pigeon/blob/master/LICENSE)

```

    MIT License

    Copyright (c) 2017 林凡荣

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

```
