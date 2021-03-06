# payment services
Epam java lab project

Servlet/JSP/JSTL/JS/Bootstrap/Maven/Tomcat 8/Log4j/MySQL

## Description:
This program is a payment system. Where you can pay for providers and check your payment history.

## Roles:
* admin (login: +7 (000) 000-00-00 | password: Admin000 | date of birth: 1994-08-30)
* user  (login: +7 (777) 777-77-77 | password: 147852aA | date of birth: 1995-09-28)

## User can:
* sign-up;
* sign-in;
* sign-out;
* recover his password with entered secret question(date of birth entered at registration);
* to top up his balance;
* select his region;
* pay for provider by category in his region and other regions too;
* check his payment history.

## Admin can:
* sign-in;
* sign-out;
* recover his password, like a user;
* to top up his balance;
* select region;
* pay for provider, like a user;
* add/edit region(name);
* add/edit category(name);
* add/edit provider(name,category,logotype);
* etc.

## Program settings:
* Intellij Idea
* jdk-1.8
* MySQL-server 5.5 and upper
* Tomcat-8.5.14 and upper

## See also:
* scripts for creating db are located in resources/database

#### Немного на русском
*От себя:* при входе в систему юзер может выбрать регион, и в зависимости от этого будет доступен тот или иной провайдер.
Также юзер может пополнять свой кошелек самостоятельно, после чего он может оплатить выбранного провайдера. Админ в свою
очередь контролирует процесс создания и редактирования данных (регионы, категории, провайдеры).

## Database diagram

![Screen Shot](http://s019.radikal.ru/i633/1705/d0/a6d211f733c3.png)
