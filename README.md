# Scala Learning

## Resources/Concepts

* SBT: http://www.scala-sbt.org/release/tutorial/
* Scala: Dean Wampler/Alex Payne, Programming Scala, 2nd Edition, O'Reilly, 2014

## Notes

### Scala IDE
* Scala IDE 4.0 RC1 at http://scala-ide.org/blog/release-notes-4.0.0-RC1.html

### ScalaTest
* Use Scala IDE 4.0 RC1
* Ensure ScalaTest Eclipse plugin is installed (Old)
  * Help - Install New Software
  * Scala IDE - http://download.scala-ide.org/sdk/helium/e38/scala211/stable/site
  * Scala IDE Plugins (incubation)
  
### sbt

```
mkdir -p src/{main,test}/{scala,java,resource}

build.sbt:
    EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.Unmanaged, EclipseCreateSrc.Source, EclipseCreateSrc.Resource)
sbt
> ~ compile #Interactive/triggered execution when saving changes
```

    sbt eclipse

Did not add src/{main,test}/resource

* Settings must be separated by blank lines


