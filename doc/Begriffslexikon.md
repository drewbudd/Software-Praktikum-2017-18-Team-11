# Begriffslexikon


## Komponenten

Für genauere Beschreibungen der einzelnen Komponenten siehe die Architektur-Dokumentation (Entwurf.md).

- **View** (engl. "component"): In dieser Komponente werden alle seperaten Views hinzugefügt.

- **Controller**: Alle Klassen, die die Logik für die View bereitstellen, befinden sich in Controller.

- **Model**: Alle Models werden hier bereitgestellt. Zu diesen Models gehören die 3 "Hauptmodels": **User**, **Field** und **DamageEvent**.

- **(App-)Services**: Hier werden alle Services über AppRegistry bereitgestellt. Diese sind von der ganzen App aus zugreifbar, wodurch es nun möglich ist gespeicherte Daten von einem Ort zu holen.

  - **MapService**: Sie ist die zentrale Komponente, die alle Features enthält, bei denen man mit der Karte arbeitet. Dazu gehören:
        - Map-Bibliothek bereitstellen
        - Felder, Schäden brechnen
        - Mapdaten aktualisieren
        - Funktionen speichern

  - **DataService**: Hier werden alle weiteren Daten (Nutzerprofile, Verträge, Angemeldeter Benutzer) bereitgestellt.

  - **CacheService**: In dieser Komponente sind zwischengespeicherte Daten, wie z.B. die heruntergeladenen Karten, um einen schnelleren Zugriff zu erlauben.

  - **ConfigService**: Alle benutzerspezifische Daten befinden sich hier, also beispielsweise Benutzerpräferenzen wie z.B. automatisches anmelden.

  - **DataStorageService**: Dieser Service ist das Bindeglied für die lokale Datenhaltung. Arten der Speicherung sind: Datenbank, XML und Textdateien. Um Daten persistent zu machen, greift jeder andere Service auf diesen hier zu. Außerdem werden die Export- und Import-Funktionen in dieser Komponente bereitgestellt.

- **User**: Diese Klasse hat zwei Unterklassen: Gutachter und Landwirt. Diese zwei Unterklassen stellen die Benutzer dar und stellen je nach Rolle (Gutachter oder Landwirt) bestimmte Funktionen bereit. Während der Gutachter Zugriff auf Felder von allen Landwirten hat, hat der Landwirt nur Zugriff auf seine eigenen Felder.


## Benutzer

- **Benutzer**: Ein Benutzer meldet sich im System als Gutachter oder Landwirt an und bekommt einen Benutzernamen und ein Passwort. Je nach Rolle hat der Benutzer bestimmte Funktionen die er nutzen kann. 

- **Gutachter**: Ein Gutachter ist eine der zwei Rollen im System. Als Gutachter stehen einem alle Funktionen der App zur Verfügung. Der Gutachter hat Zugriff auf alle Felder seiner Kunden, d.h. sobald ein Landwirt seine Felder bei einem Versicherungsunternehmen mit einem Vertrag versichert, hat jeder Gutachter der bei diesem Versicherungsunternehmen arbeitet Zugriff auf die Felder dieses Landwirten.

- **Landwirt**: Ein Landwirt ist eine der zwei Rollen im System. Als Landwirt stehen einem alle Funktionen der App zu Verfügung. Man hat als Landwirt nur Zugriff auf die eigenen Felder.


## Begriffe

- **Rolle**: Die Rolle beschreibt den Benutzer und damit seine zur Verfügung stehenden Funktionen.

- **Rechte**: Die Rechte beschreiben, ob ein Benutzer das Recht hat eine bestimmte Funktion zu nutzen, was wiederum von der Rolle des Benutzers abhängt.

- **Funktionen**: Unter Funktionen versteht man jegliche Operationen, die man als Benutzer hat wenn man die App nutzt. Darunter fallen also z.B. Felder/Schadensfälle bearbeiten, erfassen, löschen und vieles mehr. Je nach Rolle, werden nach dem Anmelden alle Funktionen dem Benutzer zugänglich gemacht, auf die er das Recht hat zuzugreifen. Der Anwendungsbereich ist auf der Karte und im slideup.

- **Karte**: Die Karte ist der Anwendungsbreich für eine Teilmenge aller Funktionen (z.B. Feld/Schadensfall erfassen, löschen, bearbeiten). Dort werden ebenfalls alle, je nach Rolle, zugängliche Felder dargestellt.

- **Feld**: Ein Feld ist ein (Versicherungs-)Objekt, das mit der Angabe der Koordinaten oder der Koordinaten der ausgewählten Eckpunkten zu einem Polygon verbunden und mit den Feldinformationen erstellt werden kann. Die Erfassung kann dabei per Touch oder per GPS erfolgen. Das Feld wird dann auf der Karte angezeigt und gehört einem Landwirt.

- **Schadensfall**: Ein Schadensfall ist ein Objekt, das mit der Angabe des Versicherungsobjekts (Name des Versicherungsnehmers, Fläche und Koordinaten des Objekts, Region (mind. Landkreis)), Schadensinformationen (Status des Schadens, Foto vom Schaden, Skalierung für Schadenshöhe, Schadensart, Schadensfläche, Schadensposition, Schadens-Koordinaten/-Polygon, Datum) und Name des Gutachters erfasst werden kann. Die Erfassung von Schadensfällen/-Koordinaten verwendet tatsächliche Sensorwerte eines Positionssensors im Gerät. Diese Erfassung findet ebenfalls auf der Karte statt. Dabei stellt ein Schadensfall ein Objekt dar, was sich in einem Feld befindet. Der Schadensfall gehört einem Landwirt.

- **Feldinformation**: Zu den Feldinformationen zählen: Feldkoordinaten, Feldart, Verträge, Erstellungsdatum, Feldart, Größe und der Besitzer. Diese Feldinformationen gehören zu dem Objekt Feld und werden benötigt, um ein Feld zu erfassen.

- **Schadensinformation**: Zu den Schadensinformationen gehören: Status des Schadens, Foto vom Schaden, Skalierung für Schadenshöhe, Schadensart, Schadensfläche, Schadensposition, Schadens-Koordinaten/-Polygon und das Datum. Diese Schadensinformationen gehören zu dem Objekt Schaden(sfall) und werden benötigt, um ein Schadensfall zu erfassen.

- **Versicherungsunternehmen**: Das Versicherunsunternehmen ist ein Unternehmen, welches Gutachter als Angestellte hat und Vertrag (/Verträge) hat. Landwirte können ihre Felder bei einem Versicherungsunternehmen versichern. 

- **Vertrag**: Ein Vertrag versichert ein Feld von einem Landwirt bei einem Versicherungsunternehmen.