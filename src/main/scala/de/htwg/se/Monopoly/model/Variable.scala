package de.htwg.se.Monopoly.model

object Variable extends Enumeration {
  val INITIAL_PLAYER_MONEY = 1500
  val TOTAL_NUMBER_OF_FIELDS = 40
  val MONEY_NEW_ROUND = 200

  val START_BOARD: Vector[Field] = Vector[Field](
    SpecialField(0, "Los"),
    Street(1, "Badstrasse", NeighbourhoodTypes.Brown, 60, 2),
    ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1),
    Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4),
    Tax(4, "Einkommenssteuer", 200),
    Street(5, "Südbahnhof", NeighbourhoodTypes.Station, 200, 25),
    Street(6, "Chausseestrasse", NeighbourhoodTypes.Blue, 100, 6),
    ChanceCard(7, "Ereignisfeld", 0, 0, 0, -1),
    Street(8, "Elisenstrasse", NeighbourhoodTypes.Blue, 100, 6),
    Street(9, "Poststrasse", NeighbourhoodTypes.Blue, 120, 8),
    SpecialField(10, "Gefängnis"),
    Street(11, "Seestraße", NeighbourhoodTypes.Pink, 140, 10),
    Street(12, "Elektrizitätswerk", NeighbourhoodTypes.Utility, 150, 0),
    Street(13, "Hafenstrasse", NeighbourhoodTypes.Pink, 140, 10),
    Street(14, "Neuestrasse", NeighbourhoodTypes.Pink, 160, 12),
    Street(15, "Westbahnhof", NeighbourhoodTypes.Station, 200, 25),
    Street(16, "Münchner Strasse", NeighbourhoodTypes.Orange, 180, 14),
    ChanceCard(17, "Gemeinschaftsfeld", 0, 0, 0, -1),
    Street(18, "Wiener Strasse", NeighbourhoodTypes.Orange, 180, 14),
    Street(19, "Berliner Strasse", NeighbourhoodTypes.Orange, 200, 16),
    SpecialField(20, "Frei Parken"),
    Street(21, "Theaterstrasse", NeighbourhoodTypes.Red, 220, 18),
    ChanceCard(22, "Ereignisfeld", 0, 0, 0, -1),
    Street(23, "Museumstrasse", NeighbourhoodTypes.Red, 220,18),
    Street(24, "Opernplatz", NeighbourhoodTypes.Red, 240, 20),
    Street(25, "Nordbahnhof", NeighbourhoodTypes.Station, 200, 25),
    Street(26, "Lessingstrasse", NeighbourhoodTypes.Yellow, 260, 22),
    Street(27, "Schillerstrasse", NeighbourhoodTypes.Yellow, 260, 22),
    Street(28, "Wasserwerk", NeighbourhoodTypes.Utility, 150, 0),
    Street(29, "Goethestrasse", NeighbourhoodTypes.Yellow, 280, 24),
    SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis"),
    Street(31, "Rathhausplatz", NeighbourhoodTypes.Green, 300, 26),
    Street(32, "Hauptstrasse", NeighbourhoodTypes.Green, 300, 26),
    ChanceCard(33, "Gemeinschaftsfeld", 0, 0, 0, -1),
    Street(34, "Bahnhofstrasse", NeighbourhoodTypes.Green, 320, 28),
    Street(35, "Hauptbahnhof", NeighbourhoodTypes.Station, 200, 25),
    ChanceCard(36, "Ereignisfeld", 0, 0, 0, -1),
    Street(37, "Parkstrasse", NeighbourhoodTypes.Purple, 350, 35),
    Tax(38, "Zusatzsteuer", 100),
    Street(39, "Schlossallee", NeighbourhoodTypes.Purple, 400, 50)
  )

}