# Release Notes

## 0.12.0 (December 18, 2022)
- Add more fields to the scan/graph response
- Increase Graph scan timeout to 30 minutes

## 0.11.0 (February 13, 2022)
- Breaking changes in scan/graph:
  - Package changes from com.jfrog.xray.client.services.graph.Scan to com.jfrog.xray.client.services.scan.Scan
  - Add progress argument
  - Add watches argument
- Add ignore URL and references in Vulnerability and Violation

## 0.10.0 (December 29, 2021)
- Refactor Graph and add issue id

## 0.9.0 (November 03, 2021)
- Add CVE to issue
- Support providing build to details/build API

## 0.8.0 (October 03, 2021)
- Add support for graph scan API

## 0.7.1 (June 09, 2021)
- Upgrade dependencies

## 0.7.0 (April 27, 2021)

- Add details/build API

## 0.6.0 (August 30, 2020)

- Migrate HTTP client to PreemptiveHttpClient

## 0.5.0 (December 30, 2019)

- Add support for keyStoreProvider

## 0.4.1 (August 08, 2019)

- Bug fixes

## 0.4.0 (June 25, 2019)

- Support Fixed Versions for issues
- Support http proxy
