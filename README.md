![GitHub Workflow Status](https://img.shields.io/github/workflow/status/Kerite/sos-api/push-to-docker?label=Push%20to%20docker&logo=Docker)
![GitHub](https://img.shields.io/github/license/Kerite/sos-api?label=LICENSE&logo=Github)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/Kerite/sos-api?logo=Github)

## Deployment

### Docker Compose

- Create a folder
- Copy docker-compose.yaml to this folder
- Create a .env file in this folder with content below

```
OSS_ENDPOINT= <your aliyun oss endpoint url>
OSS_ACCESS_KEY_ID=<your aliyun oss access key>
OSS_ACCESS_KEY_SECRET=<your aliyun oss access secret>
DB_URL=<your url>
DB_USER=<your database username>
DB_PASS=<your database password>
```

- run docker-compose up -d
