# Product Backlog

Hier werden **alle** Anforderungen in Form von **User Stories** geordnet aufgelistet.

## Epic 1 *Felder*

### Feature 1.1 *Felderfassung*

> Als *Benutzer*  möchte ich *ein neues Feld erstellen können, sodass ich dieses einem Vertrag zuordnen zu können.*

- Aufwandsabschätzung: M

- Akzeptanztest:
    - 
    - Felder können per GPS erfassst werden und kann danach Verträgen zugeordnet werden.
    - Felder können mittels Touch die Ecken für das Feld festlegen und die Fläche wird daraus berechnet und kann danach einem Verträgen zugeordnet werden.

### Feature 1.2 *Feldervisualierung*

> Als *Benutzer* möchte ich, *dass Schadensfälle, auch während der Erfassung, auf einer Karte angezeigt werden können.*

- Aufwandsabschätzung: XL

- Akzeptanztest:
    - Schadensfälle können in einer Kartenansicht dargestellt werden.
    - Die Kartenansicht des Schadens zeigt Polygone der versicherten Objekte.
    - Die Kartenansicht des Schadens zeigt den Schaden als Polygon/Fläche innerhalb der versicherten Objekte.
    - Die Kartenansicht inkl. der Schadensdarstellung ermöglicht mehrere Maßstäbe.
    - Die Ansicht der Polygone ist ohne Internetverbindung möglich.

### Feature 1.3 *Felderverwaltung*

> Als *Benutzer* möchte ich *meine Felder verwalten können, sodass ich eine Übersicht zu meinen bisher erfassten Felder habe.*

- Aufwandsabschätzung: M
- Akzeptanztest:
    - In der Verwaltung werden in einer Liste mit den Felder dargestellt.
    - Die Verwaltung ist ohne Internetverbindung möglich.
    - Felder können bearbeitet werden.
    - Felder können nach gelöscht werden.
    - Felder können nach Feldart, Größe, Besitzer, Vertrag gesucht werden.

### Featrue 1.4 *Datenaustausch*
> Als *Benutzer* möchte ich *meine Felder exportieren können, sodass ein anderer Benutzer diese Felder importieren kann.*

- Aufwandsabschätzung: S
    - Die Daten können in einem .zip-Datei abgespeichert werden
    - Die exportierte Datei kann importiert werden, sodass die exportierten Daten richtig gespeichert und dargestellt werden.

## Epic 2 *Schaden*

### Feature 2.1 *Schadensfallerfassung*
> Als *Gutachter* möchte ich *Schadensfälle verwalten (erfassen/bearbeiten/löschen) können*, um Details für den Schaden für die Berechnung aktualisiert zu können.

### Feature 2.2 *Schadensfallvisualisierung*
> Als *Benutzer* möchte ich meine zugänglichen Schadensfälle visualisiert haben 

### Feature 2.3 *Schadenfallverwaltung*

> Als *Gutachter* möchte ich *Schadensfälle verwalten (erfassen/bearbeiten/löschen) können.*

- Akzeptanztests:
    - Schadensfälle können mit der Angabe des Versicherungsobjekts (Name des Versicherungsnehmers, Fläche und Koordinaten des Objekts, Region (mind. Landkreis)), Schadensinformationen (Schadensfläche, Schadensposition, Schadens-Koordinaten/-Polygon, Datum) und Name des Gutachters erfasst werden.

    - Die Erfassung von Schadensfällen/-Koordinaten verwendet tatsächliche Sensorwerte eines Positionssensors im Gerät.

    - Schadensfälle sind nach dem vollständigen Schließen der App und Starten der App wieder im gleichen Zustand verfügbar.

    - Schadensfälle können während des Erfassens in der Kartenansicht (siehe Visuelle Darstellung der Schadensfälle) dargestellt werden.

    - Schadensfälle können während des Bearbeitung in der Kartenansicht (siehe Visuelle Darstellung der Schadensfälle) dargestellt werden.

    - Schadensfälle können nach Name des Versicherungsnehmers gesucht werden.

    - Schadensfälle können gelöscht werden.

    - Schadensfälle können bearbeitet werden.

    - Die Verwaltung ist ohne Internetverbindung möglich.


### Feature 2.4 *Datenaustausch*

## Epic 3 *Benutzerverwaltung*

### Feature 3.1 *Rollenzuweisung*

### Feature 3.2 *Funktionszuweisung TODO*
