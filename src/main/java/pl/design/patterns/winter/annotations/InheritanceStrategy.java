package pl.design.patterns.winter.annotations;


// Tak miejsce tego nie jest w adnotacjach ale na razie dałem tu bo nie wiem gdzie ...

// Myślę że sensowniej przesyłać ENUMa niż Stringa
// Dałem Snake_Case bo pola ENUMów są zwykle z wielkiej
public enum InheritanceStrategy {
    SINGLE_TABLE_INHERITANCE,
    CONCRETE_TABLE_INHERITANCE,
    CLASS_TABLE_INHERITANCE,

}
