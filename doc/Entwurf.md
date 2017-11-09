# Einführung

Dieser Entwurf legt unsere Lösungsstruktur fest und enthält alles, was wir benötigen, um einen Überblich über die ganze App zu bekommen.

Unsere App ist in vier große Teile aufgebaut:
- View
- Viewmodel
- Model
- App-Services

![Gubaer at the German language Wikipedia [GFDL (http://www.gnu.org/copyleft/fdl.html) or CC-BY-SA-3.0 (http://creativecommons.org/licenses/by-sa/3.0/)], via Wikimedia Commons](images/General-AppStructure.png)

## Verwendete Entwurfsmuster:
### View-Viewmodel-Model ###
MVVM wird in diesem Projekt verwendet, da dadurch sichergestellt wird, dass die App unabhängig von der GUI getestet werden kann.

### Polymorphie
Die Polymorphie wird für das Konstrukt für die AppServices ausgenutzt. Dadurch gibt es die Möglichkeit die verschiedenen Services über das Interface "AppService" zugreifen zu können.

# Komponentendiagramm

**TODO:** Komponentendiagramm der eigenen und externen Komponenten der App erstellen. EINFÜGEN

## Services

### Beschreibung
In der Komponente werden alle Services bereitgestellt, die die App verwendet und benutzt.

### Bereitgestellte Interfaces
AppService
DataService
CacheService
MapService


## Komponente 2

**TODO:** Beschreibung der Komponente inklusive seiner verwendeten und bereitgestellten Schnittstellen

## Externe Komponente 1

### Mapbox
Es wird die Bibliothek Mapbox für die Funktionen bereitgestellt:
- Kartenmaterial
- Funktionen Punkte in die Karte zu wählen
- weitere Funktionen

# Klassendiagramm

![Gubaer at the German language Wikipedia [GFDL (http://www.gnu.org/copyleft/fdl.html) or CC-BY-SA-3.0 (http://creativecommons.org/licenses/by-sa/3.0/)], via Wikimedia Commons](images/Klassendiagramm.png)

Gubaer at the German language Wikipedia [GFDL (http://www.gnu.org/copyleft/fdl.html) or CC-BY-SA-3.0 (http://creativecommons.org/licenses/by-sa/3.0/)], via Wikimedia Commons

**TODO:** Klassendiagramm der Aufteilung der eigenen Komponenten in Klassen darstellen.

## Beschreibung der wichtigen Klassenhierarchie 1

### AppServices
Alle benötigten Services erben am Ende vom Interface AppService. Damit ist es möglich mittels einem Dictionary auf die benötigten AppServices zugreifen zu können.

## Beschreibung der wichtigen Klasse 2
### DataService 
Im DataService werden alle Daten, die gespeichert und geladen werden müssen. 

### MapService
Im MapService werden alle benötigten Funktionen von der Library MapBox bereitgestellt.

### Role
Die Klasse Role und deren Subklassen Gutachter und Landwirt stellen die Benutzer dar. Sie beschreiben für die Rollen, welche Funktionen sie benutzen können.
z.B.: 
- Gutachter:
    - Zugriff auf Felder von allen Landwirte

- Landwirt:
    - Zugriff nur auf seine eigene Felder

## Beschreibung der wichtigen Klasse 3


# GUI-Skizze

![GUI-Skizze von Jan-Peter Ostberg, CC-BY-SA 4.0](sketches/GUI-Skizze.png)

GUI-Skizze von Jan-Peter Ostberg, CC-BY-SA 4.0

**TODO:** Eigene möglichst handschriftliche GUI-Skizzen erstellen und beschreiben.