# Lagom Exchangeratesapi.io

A simple currency exchange REST service build with Scala on Lagom, 
demonstrating basic integration of [exchangeratesapi.io](http://exchangeratesapi.io/) 
as an [unmanaged Service](https://www.lagomframework.com/documentation/1.4.x/scala/ServiceLocator.html#Communicating-with-external-services).

## HOWTO

1) Clone:

```bash
git clone https://github.com/manosbatsis/lagom-exchangeratesapi.git
```
2) Navigate to the project root:

```bash
cd lagom-exchangeratesapi
```
3) Build with SBT:

```bash
sbt runAll
```

4) Test

Currently only manual testing is possible due to [lagom 322](https://github.com/lagom/lagom/issues/322).

You can use a client like curl or RESTED to try the endpoint:
 
![RESTED Screenshot][rested]

## TODO

- Add caching
- Improve error handling: exchangeratesapi.io has downtime occasionally.
- Find a workaround or fix [lagom 322](https://github.com/lagom/lagom/issues/322) by adding unmanaged service support to the test service loader.
- Complete the API coverage of [exchangeratesapi.io](http://exchangeratesapi.io/).


[rested]: etc/img/rested.png "RESTED Screenshot" 