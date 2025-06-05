# Wanaku Tool - Jira

Configure the JIRA URL and access token using the following properties:

* `wanaku.tool.jira.url`
* `wanaku.tool.jira.access.token`

```shell
wanaku tools add -n "jira" --description "Fetch issue details from a Jira instance" --uri "jira://fetch" --type jira
```