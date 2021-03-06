# FSKEA - Einfach. Genau. Papierlos. Jeder Landwirt wird es lieben!

![Screenshot of FSKEA](doc/images/screenshots/app_screenshot.png)

Unsere FSKEA-App nach Login

Landwirte versichern ihre Felder gegen verschiedenste Schäden.
Ein wichtiger Parameter ist die Fläche des Feldes und die Region in der das Feld bewirtschaftet wird.
Kommt es zu einem Schaden, müssen Gutachter oder Landwirte die genaue Position und Abmessungen des Schadens erfassen können und den Sachbeareitern in der Versicherung übermitteln können.

Und genau dafür ist unsere App da. Man kann mit der App sowohl mit Touch als auch automatisch Positionsdaten eine Feld bzw. Schaden auf der Karte erstellen lassen und verwalten.

## Bereits implementierte Features
- Felder erstellen
- Schadensfälle erstellen
- Nach Versicherungsnehmer in Schadensälle suchen
- Schadensfälle nach Versicherungsnehmer suchen
-  Feld/ GPS mittels GPS-Koordinaten erstellen
-  Schadensfälle auflisten
-  Unterscheidung der Benutzer Gutachter / Landwirt
-  Felder anzeigen
-  Schäden anzeigen

#### Additional Feature
- Aktuelle Position ermitteln + Karte zentrieren
-  Login fenster
-  Felder auflisten
-  Schäden nach Typ suchen
-  Unterstützung Englisch und Deutsch
-  Benutzer erstellen und speichern
-  Anzeigen: MapStatus, Internetstatus, Eingeloggter Benutzer
- Felder löschen

## Noch Nicht implementierte Features
INFO: Alle offenen Issuses sind für den nächsten Sprint, da diese in diesem Sprint nicht geschlossen werden konnten
- Datenaustausch
- Möglichkeit die Karte auf ein bestimmtes Feld oder Schadensfall zentrieren
- Man kann die Felder bearbeiten:
 - Größe, Art, Gutachter etc.
 
### Farben für Felder ändern

- Möglichkeit die Farbe der Felder je nach Feldtyp anpassen
- 
### Suche erweitern

- Möglichkeit nach Feldtypen etc. suchen

## Installation
Anleitung für Android Studio:
1. Repository klonen: `git clone`
Nach dem klonen weiter bei 2.
2. Android Studio Projekt öffnen
3. Android Studio Projekt bauen
Handy anschließen und dies nach dem Bauen wählen
4. Android Studio Projekt im Emulator ausführen oder APK erstellen lassen


## Verwendung der App

Die Landwirte haben es schon schwer, dass Sie mit seinen Feldern genügen Ertrag und somit ihren Unterhalt bestreiten können. Zusätzlich werden die wird u.A Deutschland immer mehr bürokratischer und dadurch muss eben auch mehr Papiere ausgefüllt werden.

Dazu kommt noch, dass die Versicherung immer mehr und genauerere Daten von den Landwirten benötigt. Und genau da kommt unsere App ins Spiel.

### Genaue Daten für die Versicherung

Mit unserem App ist es später möglich die Felder und Schadensfälle so genau wie möglich in die Karte anzuzeigen und zu speichern.
Die Vorteile für den Landwirt:
- Dokumentation seiner Felder und Schadensfälle
- Abgleich der Daten mit der Versicherung
- Die Flächen können mit GPS erstellt werden, somit ist sicher gegeben, dass die Flächen auch genau so groß sind, wie sie in der Realität sind
- Es erspart dem Landwirt und auch dem Gutachter ärger, falls die falsche Fläche an die Versicherung weiter gegeben wurde


### Eigene Dokumentation
Auch der Landwirt interessiert sich für seine eigenen Felder und Hageschläden. Durch unsere App hat er die Möglichkeit
- Vergangene Schadensfälle auch nach mehreren Jahren aufzurufen
- Er kann seinen Ertrag mit der Fläche über die Jahre vergleichen
- Er kann sich mit der Dokumentation in den nächsten Jahren überlegen, welches Getreide er anpflanzt, sodass bei möglichen weiteren Schaden am wenigsten kaputt geht.

### Felderfassung
![Step1](doc/images/screenshots/app_menu_open.png)
![Step2](doc/images/screenshots/create_field_begin.png)

![Step3](doc/images/screenshots/create_field.png)
![Step4](doc/images/screenshots/create_field_complete.png)

### Feldvisualisierung
![Step1](doc/images/screenshots/field_visualisierung_while_creating.png)
![Step2](doc/images/screenshots/field_visualisierung_on_map.png)

### Felderverwaltung
![Step1](doc/images/screenshots/felderverwaltung.png)

### Schadensfallerfassung
![Step1](doc/images/screenshots/app_menu_open.png)
![Step2](doc/images/screenshots/create_damage.png)

![Step3](doc/images/screenshots/create_damage_complete.png)
![Step4](doc/images/screenshots/foto_damage.png)

### Schadensfallvisualisierung
![Step1](doc/images/screenshots/schadensvisualisierung.png)

### Schadensfallverwaltung
![Step1](doc/images/screenshots/shadensverwaltung.png)
![Step2](doc/images/screenshots/shadensverwaltung_search.png)

### Benutzerverwaltung
![Step1](doc/images/screenshots/login.png)
![Step2](doc/images/screenshots/language.png)

![Step3](doc/images/screenshots/register.png)
![Step4](doc/images/screenshots/register_as.png)

## Changelog

Die Entwicklungsgeschichte befindet sich in [CHANGELOG.md](CHANGELOG.md).

## Verwendete Bibliotheken
- [MapBox](https://github.com/mapbox/mapbox-gl-native)

- [AndroidSlidingUpPanel](https://github.com/umano/AndroidSlidingUpPanel)
## Lizenz

-MIT-Lizenz

Genaue Bedingungen der Lizenz können in [LICENSE](LICENSE) nachgelesen werden.

## Teamwork

### Entwicker

| Andrew Almaguer| st152650@stud.uni-stuttgart.de |

| Stefan Zindl | st148777@stud.uni-stuttgart.de |

| Aimn Ahmed | st150637@stud.uni-stuttgart.de |

### Betreuer
| Verena Käfer |
| ----------|
| Kai Mindermann |

### Tutor
|Jingxi Zhang |
