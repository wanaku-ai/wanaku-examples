# Wanaku Tool - Jira

Write the description of the tool here

```shell
wanaku tools add -n "jira" --description "Fetch issue details from a Jira instance" --uri "jira://fetch" --type jira
```

```shell
wanaku targets tools configure --service=jira --option=jiraUrl --value=https://issues.server.com/jira/
```

```shell
wanaku targets tools configure --service=jira --option=accessToken --value=YOUR-JIRA-KEY
```