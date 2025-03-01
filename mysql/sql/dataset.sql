create database if not exists globetrotter;
use globetrotter;

Drop table if exists places;
CREATE TABLE if not exists places (
	id Varchar(32) primary key,
    alias VARCHAR(50) unique not null,
    name VARCHAR(100) not null,
    clues JSON not null,
    fun_facts JSON not NULL
);

Drop table if exists submissions;
CREATE TABLE if not exists submissions (
    id Varchar(32) primary key,
    is_deleted boolean default false,
    user_id Varchar(32) not null,
    question_id VARCHAR(32) not null,
    choice VARCHAR(32) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-1","Paris",'["City of Lights","Home of the Eiffel Tower"]','["The Louvre is the most visited art museum in the world.","Paris has only one stop sign in the entire city."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-2","Tokyo",'["Capital of Japan","Known for its technology and anime"]','["Tokyo has the world\'s busiest railway station, Shinjuku.","There are more than 160,000 restaurants in Tokyo."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-3","Rome",'["Eternal City","Home of the Colosseum"]','["Rome has over 280 fountains and more than 900 churches.","Cats have special legal protection in Rome."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-4","London",'["Capital of England","Home of Big Ben"]','["London has over 170 museums.","The black cabs in London have to pass a strict test called \'The Knowledge\'."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-5","New York City",'["The Big Apple","Home of the Statue of Liberty"]','["New York City has over 800 languages spoken.","The Empire State Building has its own zip code."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-6","Sydney",'["Home of the Opera House","Largest city in Australia"]','["Sydney has over 100 beaches.","The Sydney Harbour Bridge is nicknamed \'The Coathanger\'."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-7","Barcelona",'["Known for Gaudi\'s architecture","Capital of Catalonia"]','["Barcelona has over 4.5 kilometers of beaches.","La Rambla is a famous pedestrian street in Barcelona."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-8","Dubai",'["Known for its luxury and skyscrapers","Located in the UAE"]','["Dubai is home to the world\'s tallest building, the Burj Khalifa.","Dubai has man-made islands shaped like palm trees and a world map."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-9","Amsterdam",'["Known for its canals","Capital of the Netherlands"]','["Amsterdam has more bicycles than people.","The city is built on millions of wooden poles."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-10","Rio de Janeiro",'["Home of Christ the Redeemer","Known for its beaches"]','["Rio de Janeiro hosts the famous Carnival festival.","Copacabana beach is one of the most famous beaches in the world."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-11","Beijing",'["Capital of China","Home of the Forbidden City"]','["Beijing has seven UNESCO World Heritage Sites.","The Great Wall of China is visible from space."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-12","Cairo",'["Capital of Egypt","Home of the Pyramids of Giza"]','["Cairo is the largest city in Africa.","The Nile River flows through Cairo."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-13","Berlin",'["Once divided by a wall","Capital of Germany"]','["The Berlin Wall fell in 1989.","It has more bridges than Venice."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-14","Moscow",'["Capital of Russia","Home of the Kremlin"]','["Moscow has over 600 churches.","The Moscow Metro is known for its elaborate architecture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-15","Istanbul",'["Straddles two continents","Known for the Hagia Sophia"]','["Istanbul was formerly known as Constantinople.","The Grand Bazaar is one of the oldest and largest covered markets in the world."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-16","Bangkok",'["Capital of Thailand","Known for its street food and temples"]','["Bangkok is officially known as Krung Thep Maha Nakhon.","It has floating markets."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-17","Cape Town",'["Located at the southern tip of Africa","Home of Table Mountain"]','["Cape Town is known for its diverse flora and fauna.","It has a penguin colony at Boulders Beach."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-18","Buenos Aires",'["Capital of Argentina","Known for tango"]','["Buenos Aires is often called the \'Paris of South America\'.","It has more bookstores per capita than any other city."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-19","Toronto",'["Largest city in Canada","Known for the CN Tower"]','["Toronto is one of the most multicultural cities in the world.","It has a network of underground pedestrian walkways called PATH."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-20","Seoul",'["Capital of South Korea","Known for its K-pop and technology"]','["Seoul has a high-speed internet infrastructure.","It has many palaces and historical sites."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-21","Vienna",'["Capital of Austria","Known for its classical music"]','["Vienna is often called the \'City of Music\'.","It has a long history of coffeehouse culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-22","Dublin",'["Capital of Ireland","Known for its pubs and Guinness"]','["Dublin has a rich literary history, with writers like James Joyce.","It has many green spaces and parks."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-23","Prague",'["Capital of the Czech Republic","Known for its medieval architecture"]','["Prague has a famous astronomical clock.","It\'s known for its beer culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-24","Singapore",'["City-state","Known for its cleanliness and gardens"]','["Singapore has a famous indoor waterfall at Gardens by the Bay.","It has strict laws and regulations."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-25","Helsinki",'["Capital of Finland","Known for its design and sauna culture"]','["Helsinki has many public saunas.","It has a unique church built into a rock."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-26","Lisbon",'["Capital of Portugal","Known for its trams and tiles"]','["Lisbon has a famous yellow tram system.","It\'s known for its \'azulejo\' tiles."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-27","Copenhagen",'["Capital of Denmark","Known for its design and cycling culture"]','["Copenhagen has a famous statue of the Little Mermaid.","It\'s known for its sustainable living practices."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-28","Stockholm",'["Capital of Sweden","Built on 14 islands"]','["Stockholm is home to the Nobel Prize ceremonies.","It has a unique subway art system."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-29","San Francisco",'["Known for the Golden Gate Bridge","Home of cable cars"]','["San Francisco has a famous island prison called Alcatraz.","It\'s known for its steep hills."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-30","Los Angeles",'["Home of Hollywood","City of Angels"]','["Los Angeles has a famous Walk of Fame.","It has a diverse culinary scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-31","Chicago",'["Known for its architecture","The Windy City"]','["Chicago has a famous deep-dish pizza.","It has a beautiful waterfront along Lake Michigan."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-32","Vancouver",'["Located in Canada","Surrounded by mountains and water"]','["Vancouver has a famous suspension bridge called Capilano Suspension Bridge.","It\'s known for its outdoor activities."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-33","Mexico City",'["Capital of Mexico","Known for its ancient ruins"]','["Mexico City has a large historic center.","It has a vibrant street food scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-34","Madrid",'["Capital of Spain","Home of the Prado Museum"]','["Madrid has a famous Royal Palace.","It\'s known for its tapas culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-35","Munich",'["Located in Germany","Known for Oktoberfest"]','["Munich has a famous beer hall called Hofbräuhaus.","It has a beautiful English Garden."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-36","Florence",'["Located in Italy","Known for Renaissance art"]','["Florence has a famous Duomo.","It\'s known for its leather goods."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-37","Venice",'["Known for its canals","Built on islands"]','["Venice has gondolas as a mode of transportation.","It has a famous carnival."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-38","Jerusalem",'["Holy city for three religions","Located in the Middle East"]','["Jerusalem has the Western Wall.","It has a rich history and culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-39","Athens",'["Capital of Greece","Known for its ancient ruins"]','["Athens has the Acropolis.","It\'s the birthplace of democracy."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-40","Budapest",'["Capital of Hungary","Known for its thermal baths"]','["Budapest has a beautiful Parliament building.","It\'s divided by the Danube River."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-41","Krakow",'["Located in Poland","Known for its medieval architecture"]','["Krakow has a beautiful Main Market Square.","It has a historic Jewish quarter."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-42","Saint Petersburg",'["Located in Russia","Known for its palaces"]','["Saint Petersburg has the Hermitage Museum.","It was once the capital of Russia."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-43","Kyoto",'["Located in Japan","Known for its temples and gardens"]','["Kyoto has a famous bamboo forest.","It was once the capital of Japan."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-44","Shanghai",'["Located in China","Known for its skyline"]','["Shanghai has the Oriental Pearl Tower.","It\'s a major financial center."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-45","Hong Kong",'["Special Administrative Region of China","Known for its harbor"]','["Hong Kong has a famous skyline.","It\'s a major shopping destination."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-46","Mumbai",'["Located in India","Known as the \'City of Dreams\'"]','["Mumbai has the Gateway of India.","It\'s home to Bollywood."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-47","Delhi",'["Capital of India","Known for its historical monuments"]','["Delhi has the Red Fort.","It\'s a major political center."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-48","Jaipur",'["Located in India","Known as the \'Pink City\'"]','["Jaipur has the Hawa Mahal.","It\'s known for its textiles."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-49","Agra",'["Located in India","Home of the Taj Mahal"]','["Agra has the Agra Fort.","It\'s a popular tourist destination."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-50","Varanasi",'["Located in India","Known as the \'Holy City\'"]','["Varanasi has the Ganges River.","It\'s a major pilgrimage site for Hindus."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-51","Kathmandu",'["Capital of Nepal","Known for its temples and mountains"]','["Kathmandu has many UNESCO World Heritage Sites.","It\'s a gateway to the Himalayas."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-52","Hanoi",'["Capital of Vietnam","Known for its Old Quarter"]','["Hanoi has a famous water puppet show.","It has a rich history and culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-53","Ho Chi Minh City",'["Located in Vietnam","Formerly known as Saigon"]','["Ho Chi Minh City has a vibrant nightlife.","It has many museums and historical sites."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-54","Phnom Penh",'["Capital of Cambodia","Located on the Mekong River"]','["Phnom Penh has the Royal Palace.","It has a somber history related to the Khmer Rouge."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-55","Yangon",'["Located in Myanmar","Known for its pagodas"]','["Yangon has the Shwedagon Pagoda.","It\'s a major commercial center."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-56","Manila",'["Capital of the Philippines","Located on Manila Bay"]','["Manila has a rich colonial history.","It has a vibrant street food scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-57","Jakarta",'["Capital of Indonesia","Located on the island of Java"]','["Jakarta is a major economic hub.","It has a mix of modern and traditional culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-58","Kuala Lumpur",'["Capital of Malaysia","Known for the Petronas Towers"]','["Kuala Lumpur has a diverse population.","It has a vibrant street market scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-59","Perth",'["Located in Western Australia","Known for its beaches"]','["Perth is one of the most isolated major cities in the world.","It has a relaxed lifestyle."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-60","Auckland",'["Located in New Zealand","Known as the \'City of Sails\'"]','["Auckland has a beautiful harbor.","It\'s a major cultural center."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-61","Wellington",'["Capital of New Zealand","Known for its arts and culture"]','["Wellington is known for its film industry.","It has a vibrant coffee culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-62","Honolulu",'["Located in Hawaii","Known for its beaches and surfing"]','["Honolulu has a famous Waikiki Beach.","It has a rich Polynesian culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-63","Anchorage",'["Located in Alaska","Known for its wilderness"]','["Anchorage has many outdoor activities.","It\'s a gateway to national parks."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-64","Seattle",'["Located in Washington","Known for its coffee and tech industry"]','["Seattle has the Space Needle.","It\'s known for its rainy weather."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-65","Portland",'["Located in Oregon","Known for its food trucks and breweries"]','["Portland is known for its eco-friendly practices.","It has a vibrant arts scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-66","Las Vegas",'["Located in Nevada","Known for its casinos and entertainment"]','["Las Vegas is known for its bright lights.","It has many world-class shows."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-67","Miami",'["Located in Florida","Known for its beaches and nightlife"]','["Miami has a vibrant Latin American culture.","It\'s known for its Art Deco architecture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-68","New Orleans",'["Located in Louisiana","Known for its jazz music and Mardi Gras"]','["New Orleans has a unique Creole cuisine.","It has a rich history and culture."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-69","Austin",'["Capital of Texas","Known for its live music and food trucks"]','["Austin is known as the \'Live Music Capital of the World\'.","It has a growing tech industry."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-70","Nashville",'["Located in Tennessee","Known for its country music"]','["Nashville is known as \'Music City\'.","It has a vibrant nightlife."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-71","Denver",'["Capital of Colorado","Known for its mountains and outdoor activities"]','["Denver is known as the \'Mile High City\'.","It has a thriving craft beer scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-72","Salt Lake City",'["Capital of Utah","Known for its skiing and Mormon history"]','["Salt Lake City has Temple Square.","It hosted the 2002 Winter Olympics."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-73","Minneapolis",'["Located in Minnesota","Known for its lakes and parks"]','["Minneapolis and St. Paul are known as the \'Twin Cities\'.","It has a vibrant theater scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-74","St. Louis",'["Located in Missouri","Known for the Gateway Arch"]','["St. Louis has a rich history related to the Mississippi River.","It has a famous botanical garden."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-75","Atlanta",'["Capital of Georgia","Known for its role in the Civil Rights Movement"]','["Atlanta is the headquarters of many major corporations.","It has a vibrant music scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-76","Philadelphia",'["Located in Pennsylvania","Known for its history and the Liberty Bell"]','["Philadelphia is known as the \'City of Brotherly Love\'.","It has a famous art museum featured in the \'Rocky\' movies."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-77","Boston",'["Capital of Massachusetts","Known for its history and universities"]','["Boston has the famous Freedom Trail.","It\'s known for its seafood, especially clam chowder."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-78","Washington D.C.",'["Capital of the United States","Known for its monuments and museums"]','["Washington D.C. has many free museums.","It\'s home to the White House and the Capitol Building."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-79","Detroit",'["Located in Michigan","Known as \'Motor City\'"]','["Detroit is the birthplace of Motown music.","It has a rich industrial history."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-80","Cleveland",'["Located in Ohio","Known for the Rock & Roll Hall of Fame"]','["Cleveland has a vibrant arts scene.","It\'s located on Lake Erie."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-81","Pittsburgh",'["Located in Pennsylvania","Known for its bridges and steel history"]','["Pittsburgh is known as the \'City of Bridges\'.","It has a revitalized downtown area."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-82","Cincinnati",'["Located in Ohio","Known for its chili and historic architecture"]','["Cincinnati has a famous zoo.","It\'s located on the Ohio River."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-83","Indianapolis",'["Capital of Indiana","Known for the Indianapolis 500"]','["Indianapolis has a large children\'s museum.","It\'s a major transportation hub."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-84","Columbus",'["Capital of Ohio","Known for its universities and parks"]','["Columbus has a vibrant arts scene.","It\'s a growing tech hub."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-85","Charlotte",'["Located in North Carolina","Known for its banking industry"]','["Charlotte has a growing culinary scene.","It\'s a major sports city."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-86","Raleigh",'["Capital of North Carolina","Known for its research and technology"]','["Raleigh is part of the Research Triangle Park.","It has a growing craft beer scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-87","Orlando",'["Located in Florida","Known for its theme parks"]','["Orlando is home to Walt Disney World.","It\'s a major tourist destination."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-88","Tampa",'["Located in Florida","Known for its beaches and Cuban heritage"]','["Tampa has a historic Ybor City district.","It\'s known for its cigar industry."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-89","San Diego",'["Located in California","Known for its beaches and zoo"]','["San Diego has Balboa Park.","It\'s known for its craft beer scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-90","Sacramento",'["Capital of California","Known for its history and farm-to-fork cuisine"]','["Sacramento has a historic Old Sacramento district.","It\'s located at the confluence of two rivers."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-91","Phoenix",'["Capital of Arizona","Known for its desert landscape and golf"]','["Phoenix has a vibrant arts scene.","It\'s known for its warm weather."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-92","Tucson",'["Located in Arizona","Known for its desert landscape and Mexican heritage"]','["Tucson has Saguaro National Park.","It\'s known for its unique culinary scene."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-93","Albuquerque",'["Located in New Mexico","Known for its hot air balloon festival"]','["Albuquerque has a rich Native American and Hispanic history.","It\'s known for its scenic views of the Sandia Mountains."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-94","Santa Fe",'["Capital of New Mexico","Known for its arts and adobe architecture"]','["Santa Fe has a vibrant arts scene.","It\'s known for its unique southwestern cuisine."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-95","Oklahoma City",'["Capital of Oklahoma","Known for its cowboy culture and museums"]','["Oklahoma City has the National Cowboy & Western Heritage Museum.","It\'s known for its revitalized Bricktown district."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-96","Kansas City",'["Located in Missouri and Kansas","Known for its barbecue and jazz music"]','["Kansas City has a vibrant jazz scene.","It\'s known for its many fountains."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-97","Omaha",'["Located in Nebraska","Known for its zoo and Berkshire Hathaway headquarters"]','["Omaha has the Henry Doorly Zoo and Aquarium.","It\'s a major transportation hub."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-98","Des Moines",'["Capital of Iowa","Known for its insurance industry and state fair"]','["Des Moines has a vibrant arts scene.","It\'s known for its friendly atmosphere."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-99","Sioux Falls",'["Located in South Dakota","Known for its waterfalls and parks"]','["Sioux Falls has Falls Park.","It\'s a growing city with a strong economy."]');

insert into places values (CONCAT('PLC', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 29)),"destination-100","Fargo",'["Located in North Dakota","Known for its friendly people and winter weather"]','["Fargo has a vibrant arts scene.","It\'s known for its Scandinavian heritage."]')