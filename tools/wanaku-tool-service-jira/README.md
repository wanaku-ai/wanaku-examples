# Wanaku Tool - Jira

Configure the JIRA URL and access token using the following properties:

As such, to include a tool using Jira, first create a configuration (for instance, named `jira-configuration.properties`) for it. For instance:

```properties
query.jiraUrl=<url to your Jira instance>
```

Jira also needs an access token to work. As such, you can create a file named `jira-secrets.properties` with the following contents:

```properties
query.accessToken=<my token goes here>
```


```shell
wanaku tools add -n "jira" --description "Fetch issue details from a Jira instance" --uri "jira://fetchIssue" --type jira --secrets-from-file /path/to/jira-secrets.properties --configuration-from-file /path/to/jira-configuration.properties
```