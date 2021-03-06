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

You can use a client to try the endpoint like curl:

```bash
curl --header "Content-Type: application/json" \
  --request POST \
  --data "{\"fromCurrency\": \"GBP\",\"toCurrency\" : \"EUR\",\"amount\" : 102.6}" \
  http://localhost:9000/api/convert
 ```
  or RESTED:
  
![RESTED Screenshot][rested]

## TODO

- [#1](https://github.com/manosbatsis/lagom-exchangeratesapi/issues/1) Add caching.
- [#2](https://github.com/manosbatsis/lagom-exchangeratesapi/issues/2) Improve error handling: exchangeratesapi.io has downtime occasionally.
- [#3](https://github.com/manosbatsis/lagom-exchangeratesapi/issues/3) Find a workaround or fix [lagom 322](https://github.com/lagom/lagom/issues/322) by adding unmanaged service support to the test service loader.
- [#4](https://github.com/manosbatsis/lagom-exchangeratesapi/issues/4) Complete the API coverage of [exchangeratesapi.io](http://exchangeratesapi.io/).


[rested]: etc/img/rested.png "RESTED Screenshot"


