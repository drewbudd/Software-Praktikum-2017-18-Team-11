# Einführung

Dieser Entwurf legt unsere Lösungsstruktur fest und enthält alles, was wir benötigen, um einen Überblick über die ganze App zu bekommen.

Unsere App ist in vier große Teile aufgebaut:
- View
- Viewmodel
- Model
- App-Services

Wir haben die App so aufgebaut, damit es möglich ist verschiedene Features gleichzeigt programmieren zu können. Somit kann jedes Team-Mitglied unabhängig von anderen arbeiten.
![Appaufbau](images/General-AppStructure.png)

## Verwendete Entwurfsmuster:
### Model View View-Model ###
MVVM wird in diesem Projekt verwendet, da dadurch sichergestellt wird, dass die App unabhängig von der GUI getestet werden kann.

### Service-Provider
Im Package Service werden über AppRegistry Services bereitgestellt, die von der ganzen App aus zugreifbar sind. Dadurch ist es möglich gespeicherte Daten von einem Ort zu holen.

# Komponentendiagramm

![Komponentendiagramm](Entwurf/cd1.jpg)

## Services

### Beschreibung
Die Komponente Services stellt die bereitgestellten Service als Unterkomponente bereit. Dazu gehört zum Beispiel der Map-Service.

Das Ziel ist es, dass einfach weitere Services hinzugefügt werden können, aber auch austauschbar. Z.B.:, wenn man verschiedene Libraries für eine Karte bereitstellen möchte. Zusätzlich wird das drei Schichtenmodell gewährleistet.

### Bereitgestellte Interfaces
    -ServiceProvider
    -DataServiceProvider
    -CacheServiceProvider
    -MapServiceProvider
    -MapBoxProvider
    -SetupProvider

## View

In der View werden alle seperaten Views hinzugefügt.

## Viewmodel

In dieser Komponente werden alle Klassen drin sein, welche die Logik für die View bereitstellt.

## Setup

In der Komponente Setup werden die Services und andere Einstellungen für die spätere Verwendung vorbereitet.

# Klassendiagramm
In diesem Kapitel werden die Klassen in Packages gegliedert.
## Setup

![test](Entwurf/Klassendiagrammv0.4doneSetup.png)

### Beschreibung

Im Package Setup werden nur die Services initialisiert. Die Viewmodels können von der Klasse aus, auf die verschiedenen Services zugreifen.

## View
In diesem Package werden alle Views bereitsgestellt. In userem Fall entspricht dies unseren Activities.
![test](Entwurf/Klassendiagrammv0.4doneView.png)

## Viewmodel
In dem Package Viewmodel wird die Logik der Views bereitgestellt.
![test](Entwurf/Klassendiagrammv0.4doneViewModel.png)


## Model

In diesem Package werden alle Models bereitgestellt.
Grundsätzlich haben wir 3 "Hauptmodels"
 - User
 - Field
 - DamageEvent

![Model](Entwurf/Klassendiagrammv0.4doneModel.png)

Diese benötigen die Helfer-Models siehe Diagramm.

## Services
Alle benötigten Services erben am Ende vom Interface AppService. Damit ist es möglich mittels einem Dictionary auf die benötigten AppServices zugreifen zu können.

![](Entwurf/Klassendiagrammv0.4doneServices.png)

### MapService
Der MapService hat u.A. folgende Funktionen:
- Map-Bibliothek bereitstellen
- Felder berechnen
- Schäden berechnen
- Mapdaten aktualisieren
- Funktionen speichern

Es ist die zentrale Komponente alle Features zu realisieren, die mit der Karte arbeiten.

### DataService
Im Dataservice werden alle weiteren Daten bereitgestellt. Dazu gehören:
- Nutzerprofile
- Verträge
- Angemeldeter Benutzer

Im Prinzip werden hier alle Daten abgerufen, die nichts mit der Karte zu tun hat und wichtig sind zu speichern.

### CacheService
Im CacheService werden Daten abgelegt, welche Zwischengespeichert werden sollen. Dazu gehört z.b.:
- Heruntergeladene Karten, um einen schnelleren Zugriff zu erlauben

### ConfigService
In diesem Service werden alle Daten gespeichert, welche benutzerspezifisch sind:
- Benutzerpräferenzen wie  z.B.: automatisch anmelden.

### DataStorageService
Dieser Service ist das Bindeglied für die lokale Datenhaltung. Dadurch ist es möglich die Art des speichern einfach beeinflussen zu können.
Arten der Speicherung:
- Datenbank
- XML
- Textdateien

Zusätzlich werden dort die Export- und Import- Funktionen bereitgestellt.
Jeder andere Service greift auf diesen zu, um Daten persistent zu speichern.

## User
Die Klasse User und deren Subklassen Gutachter und Landwirt stellen die Benutzer dar. Sie beschreiben welche Funktionen die rollen benutzen können.
Z.B.: 
- Gutachter:
    - Zugriff auf Felder von allen Landwirten

- Landwirt:
    - Zugriff nur auf seine eigenen Feldern

# GUI-Skizze

Startansicht: Login

![Login](Entwurf/Login.png)

Die Loginpage wird angezeigt, wenn man die App startet. Nach dem Login können folgende Features genutzt werden.

![Feature 1.1 & 1.2](Entwurf/Feature1112.png)

Feature 1.1 & 1.2 : Felderfassung und Feldvisualisierung

Beim Eckpunkt hinzufügen, kann man entweder per Touch oder GPS (durch Touch auf ![GPS Icon](Entwurf/GPSIcon.png)) einen Punkt hinzufügen.

![Feature 1.3](Entwurf/Feature13.png)

Feature 1.3 : Felderverwaltung

Durch tappen auf ein Feld oder Navigation-Menu werden weitere Informationen angezeigt:
- Details über das Feld
- Schadensfälle, die zum Feld gehören
Die Felder werden mit verschiedenen Farben dargestellt, dies hilft dem Benutzer die Informationen schneller zuzuordnen

![Feature 2.1 & 2.2](Entwurf/Feature2122.png)

Feature 2.1 & 2.2 : Schadensfallerfassung & Schadensfallvisualisierung

Beim Hinzufügen von Schadensposition, kann man Entweder per Touch oder GPS (durch Touch auf ![GPS Icon](Entwurf/GPSIcon.png) ) die Position bestimmen.  Beim Hinzufügen von Fotos ist man durch ein Intent zum Camera-App weitergeleitet.

![Feature 2.3](Entwurf/Feature23.png)

Feature 2.3 : Schadensfallverwaltung

Durch Touch auf ein Schadenmarker oder Navigation-Menu kommt man zur mehr Informationen von den aktiven und schon bearbeiteten Schadensfällen.  Farben zeigt aktuelle Status.

![Feature 1.4 & 2.4](Entwurf/Feature1424.png)

Feature 1.4 & 2.4 : Datenaustausch

Beim Exportieren ist es möglich auf dem Gerät zu speichern oder direct per E-Mail schicken.  Beim importieren soll die Datei schon im Gerätspeicher sein.
