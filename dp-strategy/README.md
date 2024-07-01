# Implementing a strategy pattern

### Step 1: Define an enumeration type
```
public enum OutputType {
    PDF,
    EXCEL,
    CSV
}
```

### Step 2: Define the interface (strategy)
```
public interface IDocumentCreator {
    String createDocument();
    OutputType getOutputType();
}
```

### Step 3: Define classes for the interface
```
public class PDFCreator implements IDocumentCreator {
    String createDocument() {
        return "PDF Document created";
    }
    
    OutputType getOutputType() {
        return OutputType.PDF;
    } 
}

public class ExcelCreator {
    String createDocument() {
        return "Created Excel document";
    }
    
    OutputType getOutputType() {
        return OutputType.EXCEL;
    }
}
```

### Step 4: Inject the creators as a List
```
public interface DocumentCreator {
    String createDocument(OutputType outputType);    
}

@RequiredArgsConstructor
public class DoocumentCreatorImpl implements DocumentCreator {
    List<IDocumentCreator> creators;
    
    public String createDocument(OutputType outputType) {
        return creators.stream()
            .filter(e -> e.getOutputType().equals(outputType))
            .findFirst()
            .orElseThrow(UnsupportedOperationException::new)
            .createDocument()
    }
}

```

### Step 5: Test the application

courtesy: 

https://dzone.com/articles/spring-strategy-pattern

https://medium.com/@halilugur/strategy-pattern-with-spring-boot-4704218c65ed