---
layout: home
---

# Available Tools Services

The following tools services can be made available using Wanaku and used to provide access to specific services.

| Type         | Service Tool                                                                 | Description                                                                 |
|--------------|------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| `exec`       | [wanaku-tool-service-exec](./wanaku-tool-service-exec/README.md)             | Executes a process as a tool (use carefully - there's no input validation)  |
| `http`       | [wanaku-tool-service-http](./wanaku-tool-service-http/README.md)             | Provides access to HTTP endpoints as tools via Wanaku                       |
| `kafka`      | [wanaku-tool-service-kafka](./wanaku-tool-service-kafka/README.md)           | Provides access to Kafka topics as tools via Wanaku                         |
| `kafka`      | [wanaku-tool-service-sqs](./wanaku-tool-service-sqs/README.md)               | Provides access to AWS SQS queues as tools via Wanaku                       |
| `tavily`     | [wanaku-tool-service-tavily](./wanaku-tool-service-tavily/README.md)         | Provides search capabilities on the Web using [Tavily](https://tavily.com/) |
| `telegram`   | [wanaku-tool-service-telegram](./wanaku-tool-service-telegram/README.md)     | Provides message notification via [Telegram](https://telegram.org/) bot     |
| `yaml-route` | [wanaku-tool-service-yaml-route](./wanaku-tool-service-yaml-route/README.md) | Provides access to Camel routes in YAML tools via Wanaku                    |

> [!NOTE]
> Some services (i.e.; Tavily, S3, etc.) may require API keys and/or other forms of authentication.
> Check the README.md files in each service documentation for more details.