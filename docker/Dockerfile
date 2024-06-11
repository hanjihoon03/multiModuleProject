# 기반 이미지 설정
FROM openjdk:21

# 애플리케이션 파일 복사
COPY project-0.0.1-SNAPSHOT.jar /usr/src/app/

# 작업 디렉토리 설정
WORKDIR /usr/src/app

# 애플리케이션 실행
CMD ["java", "-jar", "project-0.0.1-SNAPSHOT.jar"]
