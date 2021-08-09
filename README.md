# Film adatbázis (movie-database) - vizsgaremek

## Leírás

Az alkalmazással lehetőségünk van egy egyszerű filmes adartbázist létrehozni
és rajta különböző műveleteket végrehjajtani. Tudunk kezelni rendezőket és a hozzájuk tartozó filmeket.
A filmekre tudunk leadni értékeléseket és azokat le tudjuk kérdezni.

## Technológia

Háromrétegű webes alkalmazás Java Spring Boot keretrendszer használatával, amely REST webszolgáltatások segítségével kommunikál.
Az adatok tárolására MariaDB adtbázist használ, az adatbáziskezelés Spring Data JPA technológiával történik.
Az adattáblák létrehozásáért a Flyway a felelős. A felesleges gépeléstől a Lombok óv meg minket.
A DTO-k és az entitások közötti konverziót a ModelMapper végzi. A hibakezeléshez a Zalando problem-spring-web-starter modult használja.
Az alaklamzás része a Swagger UI, amelyből kinyehető az alkalmazás OpenAPI dokumentációja.


## Használat

**Entitások:** `Director`, `Movie`

### Director

**Attribútumok:**

* `id`: a rendező egyedi azonosítója
* `name`: a rendező neve (String, NotBlank, min = 3, max = 255)
* `birthday`: a rendező születési ideje (LocalDate)
* `movies`: a rendezőhöz tartozó filmek listája (OneToMany)

**Műveletek:**

| method       | endpoint                 | usage                                                                                          |
| ------------ | ------------------------ | ---------------------------------------------------------------------------------------------- |
| GET          | `"/api/directors"`       | lekéri az összes rendezőt                                                                      |
| GET          | `"/api/directors/{id}"`  | lekér egy rendezőt `id` alapján                                                                |
| POST         | `"/api/directors"`       | létrehoz egy rendezőt                                                                          |
| PUT          | `"/api/directors/{id}"`  | módosítja egy rendező adatait `id` alapján                                                     |
| DELETE       | `"/api/directors/{id}"`  | töröl egy rendezőt `id` alapján, továbbá törli a rendezőhöz tartozó filmeket és értekeléseket  |

**Adatbázis tábla:** `directors`

---

### Movie

**Attribútumok:**

* `id`: a film egyedi azonosítója
* `director`: (ManyToOne)
* `title`: a film címe (String, NotBlank, min = 1, max = 255)
* `year`: a film megjelenésénk éve (int)
* `length`: a film hossza (int)
* `ratings`: a film érkelései (List<Integer>)
* `averageRating`: értékelések átlaga, számított mező, nem kerül letárolásra az AB-ba (double, Transient)


**Műveletek:**

| method       | endpoint                               | usage                                                                         |
| ------------ | -------------------------------------- | ----------------------------------------------------------------------------- |
| GET          | `"/api/movies"`                        | lekéri az összes filmet                                                       |
| GET          | `"/api/movies/{id}"`                   | lekér egy filmet `id` alapján                                                 |
| POST         | `"/api/movies"`                        | létrehoz egy filmet                                                           |
| PUT          | `"/api/movies/{id}"`                   | módosítja egy filmet adatait `id` alapján (rendezőt nem lehet módosítani)     |
| DELETE       | `"/api/movies/{id}"`                   | töröl egy filmet `id` alapján, továbbá törli a filmhez tartozó értékeléseket  |
| GET          | `"/api/movies/{id}/ratings"`           | lekérdezi egy filmhez tartozó értekelseket                                    |
| POST         | `"/api/movies/{id}/rating/{ratings}"`  | hozzáad egíy filmhez egy értékelést                                           |

**Adatbázis tábla:** `movies`, `movies_ratings`

