# api-automation-challenge
Project for api autotests

To run local tests it needs to add the following text to
**Run/Debug Configurations -> Edit configuration templates... -> TestNG -> VM options**

```
-Dserver="https://hr-challenge.dev.tapyou.com/"
```

Allure reports can be checked in
**Maven>api-ch-four>plugins>allure>allure:serve** after tests were finished.