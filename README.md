### Steps to Run:-

### 

### Download the application and Generate Protobuf Code:
```bash
  "mvn clean install" (Verify if Java files are generated under /target/generated-sources/protobuf)
  "mvn spring-boot:run" OR Run Application from the Editor
  Note: If you face any import issue into server(ProcessServiceImpl), try moving the downloaded code under directory IdeaProjects(In case) 
```
### Use Below Payload:
```bash
curl --location 'http://localhost:8080/processData' \
--header 'Content-Type: application/json' \
--data '[55, 10, 401, 60, 901]'
```
### Responses
```bash
// List with data 
{
    "statusCode": 200,
    "message": "Filter and modified Numbers sent to gRPC service"
}
// Empty List
{
    "statusCode": 400,
    "message": "Empty List provided in the payload"
}
```

### Make sure you see logs such as
```bash
2025-02-17T00:56:30.158+05:30  INFO 7266 --- [data.processor] [ault-executor-2] c.s.d.p.server.ProcessServiceImpl        : number received  110
2025-02-17T00:56:30.160+05:30  INFO 7266 --- [data.processor] [ault-executor-2] c.s.d.p.server.ProcessServiceImpl        : number received  802
2025-02-17T00:56:30.161+05:30  INFO 7266 --- [data.processor] [ault-executor-3] c.s.d.p.client.ProcessServiceClient      : Server responded: Number Processed : 110
2025-02-17T00:56:30.161+05:30  INFO 7266 --- [data.processor] [ault-executor-2] c.s.d.p.server.ProcessServiceImpl        : number received  1802
2025-02-17T00:56:30.161+05:30  INFO 7266 --- [data.processor] [ault-executor-3] c.s.d.p.client.ProcessServiceClient      : Server responded: Number Processed : 802
2025-02-17T00:56:30.162+05:30  INFO 7266 --- [data.processor] [ault-executor-2] c.s.d.p.client.ProcessServiceClient      : Server responded: Number Processed : 1802
2025-02-17T00:56:30.163+05:30  INFO 7266 --- [data.processor] [ault-executor-2] c.s.d.p.client.ProcessServiceClient      : Data streaming done

```