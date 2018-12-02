# Spring Gateway Bug

## Empty Content Type Header
if no header `Content-Type` is returned from the downstream service then Spring-Gateway returns a `500` and not the original status code

Please run
`mvn verify` to see the test failure
