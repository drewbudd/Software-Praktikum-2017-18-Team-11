# Product Backlog

Hier werden **alle** Anforderungen in Form von **User Stories** geordnet aufgelistet.

## Epic 1 *Felder*

> Als *Benutzer* möchte ich *die Möglichkeit haben Felder auf einer Karte einzeichnen zu können, damit ich der Versicherung mitteilen kann, welche Felder zu versichern sind.*

Das Ziel des Epics ist es den Landwirten folgende Möglichkeiten zu geben:
- seine Felder auf einer Karte einzeichnen
- seine Felder zu bearbeiten
- seine Felder an andere Gutachter übermitteln

Der Landwirt hat zwei Möglichkeiten neue Felder einzeichnen zu können:
1) GPS-Signal: Mittels GPS kann der Landwirt sein Feld ablaufen und das Feld wird automatisch in die Karte eingezeichnet.
2) Touch: Der Landwirt kann mittels Touch die Eckpunkte seines Feldes auf der Karte setzen und die App berechnet daraus das Feld.

In der Feldverwaltung ist es möglich:

    - eine Liste mit den bestehenden Felder zu haben
    - Felder zu bearbeiten
    - Felder zu löschen

Mittels einem Datenaustausch ist es dem Landwirt möglich seine erstellten Felder zu exportieren, um diese dem Gutachter geben, sodass er immer die aktuellen Felder von dem Landwirt hat.

Der Gutachter kann alle Felder von seinen Kunden (Landwirte) bearbeiten und ebenfalls neue Felder hinzufügen, falls er bei einem Landwirt ist.

### Feature 1.1 *Felderfassung*

> Als *Benutzer*  möchte ich *ein neues Feld erstellen können, sodass ich alle Details über das Feld eingeben kann, um es den passenden Verträgen versicherungsgerecht zuordnen zu können.*

- Aufwandsabschätzung: M

- Akzeptanztest:
    - Das neue Feld kann mit der Angabe der Koordinaten oder der Koordinaten der ausgewählten Eckpunkten zu einem Polygon verbunden, und mit den Feldinformationen erstellt werden.
     <!-- -->
    - Feldinformationen: Feldkoordinaten, Feldart, Verträge, Erstellungsdatum.
    - Das neue Feld kann per GPS erfassst werden.
    - Das neue Feld kann per Touch erfasst werden.
    - Das neue Feld kann mehreren Verträgen zugeordnet werden.

### Feature 1.2 *Feldervisualierung*

> Als *Benutzer* möchte ich, *dass Schadensfälle, auch während der Erfassung, auf einer Karte angezeigt werden können.*

- Aufwandsabschätzung: XL

- Akzeptanztest:
    - Schadensfälle können in einer Kartenansicht dargestellt werden.
    - Die Kartenansicht des Schadens zeigt Polygone der versicherten Objekte.
    - Die Kartenansicht des Schadens zeigt den Schaden als Polygon/Fläche innerhalb der versicherten Objekte.
    - Die Kartenansicht inkl. der Schadensdarstellung ermöglicht mehrere Maßstäbe. Die Karte lässt sich zoomen.
    - Die Ansicht der Polygone ist ohne Internetverbindung möglich.

### Feature 1.3 *Felderverwaltung*

> Als *Benutzer* möchte ich *meine zugänglichen Felder verwalten können, sodass ich eine Übersicht zu meinen bisher erfassten Felder habe, Felder aktualisieren und löschen kann.*

- Aufwandsabschätzung: M

- Akzeptanztest:
    - In der Verwaltung werden die dir zugänglichen Felder in einer Liste dargestellt.
    - Die Verwaltung ist ohne Internetverbindung mögich.
    - Felder können bearbeitet werden.
    - Felder können nach gelöscht werden.
    - Felder können nach Feldart, Größe, Besitzer, Vertrag gefiltert werden.

### Feature 1.4 *Datenaustausch*

> Als *Benutzer* möchte ich *meine Felder exportieren können, sodass ein anderer Gutachter diese Felder importieren kann.*

- Aufwandsabschätzung: M

- Akzeptanztest:
    - Die Daten können in eine komprimierte Datei abgespeichert werden.
    - Die exportierte Datei kann importiert werden, sodass die exportierten Daten richtig gespeichert und dargestellt werden, sofern die Berechtigung gegeben ist.
    - Die importierte Datei soll mit existierender Daten verglichen wird, sodass schon vorhandene Daten aktualisiert werden können.

## Epic 2 *Schaden*

> Als *Benutzer* möchte ich *einen Schaden erfassen können, sodass ich diesen der Versicherung melden kann*.

Das Ziel ist es die wichtigsten Informationen eines Schadens zu erfassen. Dazu gehört:

    - Den Schaden erfassen
    - Den Schaden speichern
    - Den Schaden exportieren
Damit die Versicherung die Daten des Schadens als Versicherungsfall aufnehmen kann.

### Feature 2.1 *Schadensfallerfassung*

> Als *Benutzer* möchte ich *Schadensfälle erfassen können*, damit ich diesen der Versicherung mitteilen kann.

- Aufwandsabschätzung: M

- Akzeptanztests:
    - Schadensfälle können mit der Angabe des Versicherungsobjekts (Name des Versicherungsnehmers, Fläche und Koordinaten des Objekts, Region (mind. Landkreis)), Schadensinformationen (Status des Schadens, Foto vom Schaden, Skalierung für Schadenshöhe, Schadensart, Schadensfläche, Schadensposition, Schadens-Koordinaten/-Polygon, Datum) und Name des Gutachters erfasst werden.
    - Die Erfassung von Schadensfällen/-Koordinaten verwendet tatsächliche Sensorwerte eines Positionssensors im Gerät.

### Feature 2.2 *Schadensfallvisualisierung*

> Als *Benutzer* möchte ich *meine zugänglichen Schadensfälle visualisiert haben, um diese von der Karte aus bearbeiten und ansehen zu können.*

- Aufwandsabschätzung: XL

- Akzeptanztests:
    - Schadensfälle können während des Erfassens in der Kartenansicht (siehe Visuelle Darstellung der Schadensfälle) dargestellt werden.
    - Schadensfälle können während des Bearbeitung in der Kartenansicht (siehe Visuelle Darstellung der Schadensfälle) dargestellt werden.

### Feature 2.3 *Schadensfallverwaltung*

> Als *Gutachter* möchte ich *Schadensfälle bearbeiten können, sodass Änderungen vorgenommen werden können.*

- Aufwandsabschätzung: L

- Akzeptanztests:
    - Schadensfälle sind nach dem vollständigen Schließen der App und Starten der App wieder im gleichen Zustand verfügbar.
    - Schadensfälle können nach Name des Versicherungsnehmers, Art und Status gefiltert werden.
    - Schadensfälle können gelöscht werden.
    - Schadensfälle können bearbeitet werden.
    - Die Verwaltung ist ohne Internetverbindung möglich.

### Feature 2.4 *Datenaustausch*

> Als *Benutzer* möchte ich *meine Schadensfälle exportieren können, sodass ein anderer Gutachter diese importieren kann.*

- Aufwandsabschätzung: M

- Akzeptanztests:
    - Die Daten können in einer komprimierte Datei abgespeichert werden.
    - Die exportierte Datei kann importiert werden, sodass die exportierten Daten richtig gespeichert und dargestellt werden, sofern die Berechtigung gegeben ist.
    - Die importierte Datei soll mit existierender Daten verglichen wird, sodass schon vorhandene Daten aktualisiert werden können.

## Epic 3 *Benutzerverwaltung*

> Als *Admin* möchte ich *die Kontrolle über die Benutzer haben und ihnen eine Rolle zuweisen können, damit jeder Benutzer nur die Funktionen zur Verfügung hat, welche er benötigt.*

Diese App werden verschiedene Personas verwenden. Dadurch muss sicher gestellt werden, dass jede Rolle die passende Rechte bekommt und somit nur Zugriff auf die Funktionen bekommt, für die er berechtigt ist. 

### Feature 3.1 *Rollenzuweisung*

> Als *Admin* möchte ich *jedem registrierten Benutzer eine Rolle zuweisen können, um seinen Zugriff auf Funktionen kontrollieren zu können.*

- Aufwandsabschätzung: M

- Akzeptanztests:
    - Jeder registrierte Benutzer wird einer bestimmten Rolle zugewiesen.
    - Die Rolle des eingeloggten Benutzers stimmt mit der Rollenzuweisung überein.

### Feature 3.2 *Funktionszuweisung zur Rolle*

> Als *Admin* möchte ich *jeder Rolle Funktionen zuweisen, um eine klare Abgrenzung zwischen den Profilen herzustellen, sodass kein Missbrauch von Rechten möglich ist.*

- Aufwandsabschätzung: M

- Akzeptanztests:
    - Jede Rolle kann auf seine freigegebenen Funktionen zugreifen.
    - Funktionen werden den Rollen zugeordnet.

<!--TODO:
    Verträge als Feature ergänzen?-->