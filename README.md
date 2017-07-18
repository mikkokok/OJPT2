# Hajautetut ohjelmistot ja pilvipalvelut kurssin toinen harjoitustyö

Tässä harjoitustyössä harjoiteltiin etäkutsujen tekemistä toteuttamalla vähintään kahden pelaajan verkon yli pelattava moninpeli joka tässä tapauksessa oli ristinolla. Tässä työssä hyödynnettiin Javaan sisältyvää RMI:tä. Tarkempi tehtävänanto löytyy [tästä](Tehtävänanto).

# Ohjelman ajaminen

1.Avaa cmd ja avaa kansio jossa luokat sijaitsevat (OJPT2\bin)

2.Käännä luokka Ristinollapalvelin seuraavasti: rmic ojpt2.server.RistinollaPalvelin

3.Käännä luokka Pelaaja seuraavasti: rmic ojpt2.Pelaaja

4.Käynnistä rmiregistry komennolla start rmiregistry 

5.Nyt sekä palvelin että asiakas voidaan käynnistää normaalisti esim. Eclipsen kautta.

