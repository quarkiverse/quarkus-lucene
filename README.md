# Lucene extension for Quarkus
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-3-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.lucene/quarkus-lucene?logo=apache-maven&style=flat-square)](https://search.maven.org/artifact/io.quarkiverse.lucene/quarkus-lucene)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://opensource.org/licenses/Apache-2.0)

[Apache Lucene](https://lucene.apache.org/) is a Java library providing powerful indexing and search features, as well as spellchecking, hit highlighting and advanced analysis/tokenization capabilities.

This extension adds support for building native executables when using Apache Lucene.

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
      <td align="center" valign="top" width="14.28%"><a href="https://lesincroyableslivres.fr/"><img src="https://avatars.githubusercontent.com/u/1279749?v=4?s=100" width="100px;" alt="Guillaume Smet"/><br /><sub><b>Guillaume Smet</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-lucene/commits?author=gsmet" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="http://melloware.com"><img src="https://avatars.githubusercontent.com/u/4399574?v=4?s=100" width="100px;" alt="Melloware"/><br /><sub><b>Melloware</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-lucene/commits?author=melloware" title="Documentation">📖</a> <a href="https://github.com/quarkiverse/quarkus-lucene/commits?author=melloware" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!