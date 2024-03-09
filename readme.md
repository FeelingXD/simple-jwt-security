## SIMPLE-JWT-SECURITY

스프링 부트 3 버전 이상에서 JWT를 활용한 SPRING SECURITY 로그인 간단 예제입니다.


## Dependencies

- SpringBoot 3.x
- Spring-Security
- Spring-web
- mariadb(mysql)
- redis
- jjwt-api
- jjwt-jackson
- lombok
- **DOCKER**

## Quick-start

간단하게 구성하기위해 DOCKER를 사용 하였으며   
resource 폴더의 dockercompose를 통해 바로 실행환경을 구성할 수 있도록 작성해두었습니다.

포트 바운드는 다음과 같습니다.   
- mariadb(mysql) 3306:3306 
- redis 6379:6379