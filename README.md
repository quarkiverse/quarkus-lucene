# Lucene extension for Quarkus
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

[Apache Lucene](https://lucene.apache.org/) is a Java library providing powerful indexing and search features, as well as spellchecking, hit highlighting and advanced analysis/tokenization capabilities. 

This extension adds support for native images when using Apache Lucene for both Java 8 and 11 and GraalVM 20.0 and 21.0.

## Usage

## Configuration

After configuring the `Quarkus BOM`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-bom</artifactId>
            <version>${insert.quarkus.version.here}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

You can configure the `lucene` extension by adding the following dependency:

```xml
<dependency>
    <groupId>io.quarkiverse.lucene</groupId>
    <artifactId>quarkus-lucene</artifactId>
    <version>${insert.release.version}</version>
</dependency>
```

## Limitations

* The extension disables the workaround for bug [JDK-4724038](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4724038) in the [MMapDirectory](https://lucene.apache.org/core/8_7_0/core/org/apache/lucene/store/MMapDirectory.html). This means that files from the index will not be deleted immediately when Lucene issues a deletion operation, but only upon garbage collection, causing a temporary larger disk usage.

* Test Coverage is currently limited to ```lucene-core``` ,```lucene-analyzers-common``` and ```lucene-queryparser``` artifacts from Lucene.

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/gustavonalle"><img src="https://avatars.githubusercontent.com/u/440989?v=4?s=100" width="100px;" alt="Gustavo"/><br /><sub><b>Gustavo</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-lucene/commits?author=gustavonalle" title="Code">💻</a> <a href="#maintenance-gustavonalle" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://www.jocas.lt"><img src="https://avatars.githubusercontent.com/u/230044?v=4?s=100" width="100px;" alt="Dainius Jocas"/><br /><sub><b>Dainius Jocas</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-lucene/commits?author=dainiusjocas" title="Code">💻</a> <a href="#maintenance-dainiusjocas" title="Maintenance">🚧</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!