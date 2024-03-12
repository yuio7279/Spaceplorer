package com.spaceplorer.spaceplorerweb.common;

import com.spaceplorer.spaceplorerweb.domain.*;
import com.spaceplorer.spaceplorerweb.domain.spaceship.SpaceShip;
import com.spaceplorer.spaceplorerweb.domain.spaceship.Hhms;
import com.spaceplorer.spaceplorerweb.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


//임시 데이터 이니셜라이저
@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final SpaceShipRepository spaceShipRepository;
    private final PlanetRepository planetRepository;
    private final HhmsRepository hhmsRepository;
    private final EntertainmentRepository entertainmentRepository;
    private final LandmarkRepository landmarkRepository;

    List<SpaceShip> spaceShipList = new ArrayList<>();
    List<Planet> planetList = new ArrayList<>();
    List<City> cityList = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        //1
        initCategory();

        //2
        initSpaceShip();
        initPlanet();
        initCity();

        //3
        initHhms();
        initEntertainment();
        initHotel();
        initLandmark();
    }

    private void initLandmark() {
        if (!getCityList()) return;
        landmarkRepository.save(new Landmark("랜드마크1","전설의 랜드마크","전설의 랜드마크입니다.",cityList.get(0)));
        landmarkRepository.save(new Landmark("랜드마크2","전설의 랜드마크2","전설의 랜드마크입니다.2",cityList.get(0)));
        landmarkRepository.save(new Landmark("랜드마크3","전설의 랜드마크3","전설의 랜드마크입니다.3",cityList.get(0)));
    }



    private void initEntertainment() {
        if(!getCityList()) return;

        entertainmentRepository.save(new Entertainment("비행기탐험","비행기탐험을 하세요!","비행기탐험을 할 수 있음", cityList.get(0)));
        entertainmentRepository.save(new Entertainment("비행기탐험2","비행기탐험을 하세요!2","비행기탐험을 할 수 있음2", cityList.get(0)));
        entertainmentRepository.save(new Entertainment("비행기탐험3","비행기탐험을 하세요!3","비행기탐험을 할 수 있음3", cityList.get(0)));
    }

    private void initHhms() {
        if (!getSpaceShipList()) return;

        hhmsRepository.save(new Hhms(100000000L,50000000L, 2000000L, spaceShipList.get(0)));
        hhmsRepository.save(new Hhms(70000000L,50000000L,3000000L, spaceShipList.get(2)));
        hhmsRepository.save(new Hhms(200000000L,20000000L,5000000L, spaceShipList.get(4)));
    }



    private void initPlanet() {
        planetRepository.save(new Planet("금성(Venus)", "462℃", "243일", 108200000L, "금성의 사치스러운 금빛 동굴에서 밝고 화려한 모험을 즐기며, 태양계 내에서 가장 뜨거운 행성의 독특한 아름다움을 발견하세요, 구름 속에 숨겨진 금성의 지질학적 비밀을 탐험하는 데 매우 매력적입니다.",false));
        planetRepository.save(new Planet("달(Moon)", "-170℃ ~ 120℃", "27.3일", 380000L, "달까지의 가까운 여정에서 고요하고 차가운 밤하늘 아래 도시적 매력과 함께 신비로운 달빛을 경험하세요. 달 여행에서는 무중력 환경을 체험하고, 지구가 아닌 다른 천체의 일출과 일몰을 관찰할 수 있는 독특한 경험을 할 수 있습니다.",false));
        planetRepository.save(new Planet("화성(Mars)", "-125℃ ~ 20℃", "687일", 54600000L, "화성은 '붉은 행성'으로 알려져 있으며, 붉은 행성 화성에서는 용암의 흐름, 거대한 폭풍, 익스트림한 환경을 탐험하며, 우주의 모험심을 자극하는 도전을 경험할 수 있습니다",false));
        planetRepository.save(new Planet("천왕성(Uranus)", "-224℃", "17시간", 3200000000L, "천왕성의 얼음과 창백한 풍경 속에서 고요하고 어두운 우주의 신비를 체험하며, 태양계의 먼 구석까지 모험의 여정을 떠나보세요",true));
    }

    private void initSpaceShip() {
        spaceShipRepository.save(new SpaceShip("호버크루즈쉽(공중여객선)", 5000000L, 5000000L, 200000L, 25L, "이 혁신적인 공중여객선은 바다 위를 날아다니며, 특정 행성들의 이동을 가능하게 합니다. 화성의 붉은 사막부터 천왕성의 공중정원까지 여행하는 동안 잊을 수 없는 풍경을 경험할 수 있습니다."));
        spaceShipRepository.save(new SpaceShip("스페이스비틀(우주고속선)", 10000000L, 5000000L, 500000L,20L, "가족 단위 여행객을 위해 특별히 설계된 이 우주고속선은 단거리기준 가장 빠른 속도를 자랑하니다. 20인승으로 아늑하며, 개별 승객의 편안함을 위한 최신 편의 시설과 엔터테인먼트 시스템을 제공합니다. 가족과 함께 우주 여행의 즐거움을 만끽하세요."));
        spaceShipRepository.save(new SpaceShip("더퍼스트익스플로러쉽(호화탐험선)", 4000000L, 15000000L, 1000000L,300L, "우주의 신비를 탐험하고자 하는 이들을 위한 궁극의 호화 탐험선입니다. 300명의 탑승객을 수용할 수 있으며, 다양한 즐길거리와 함께 선내에는 연구 시설, 체육관, 스파, 여러 레스토랑 등이 마련되어 있습니다."));
        spaceShipRepository.save(new SpaceShip("스타셀레스트요트(우주요트)", 2000000L, 3000000L,400000L, 10L, "개인 맞춤형 럭셔리 우주여행을 제공하는 요트. 소규모 그룹이나 커플에게 이상적으로, 별빛 아래에서의 저녁 식사와 함께 낭만적인 우주 여행을 즐길 수 있습니다."));
        spaceShipRepository.save(new SpaceShip("갤럭시트레일블레이저(장거리탐사선)", 10000000L, 10000000L,100000L, 15L, "장기간의 우주 탐사와 연구에 적합한 우주선. 과학자와 탐험가를 위한 최신 연구 장비가 구비되어 있으며, 광대한 우주를 탐험할 수 있는 뛰어난 내구성과 자급자족 시스템을 갖추고 있습니다."));
    }

    private void initHotel() {
        if (!getCityList()) return;
        //달
            //루나리아
        hotelRepository.save(new Hotel("실버문 레지던스",5L,"이 곳의 중심에 위치해 있으며, 달빛 아래에서의 로맨틱한 밤을 제공합니다.", cityList.get(3)));
        hotelRepository.save(new Hotel("크레이터 뷰 호텔",3L,"달의 크레이터를 가까이에서 관찰할 수 있는 이색적인 경험을 선사합니다.", cityList.get(3)));
            //세레나티 베일
        hotelRepository.save(new Hotel("퀴어트 룬 호텔",4L,"이곳의 조용한 분위기를 살린 고요하고 세련된 숙박을 제공합니다.", cityList.get(4)));
        hotelRepository.save(new Hotel("달빛 호텔",5L,"달의 평화로운 정취를 만끽할 수 있습니다.", cityList.get(4)));
            //아르테미스 플레인
        hotelRepository.save(new Hotel("익스플로러스 돔",3L,"모험가들을 위해 특별히 설계된 호텔로, 평원에서의 별빛 아래 밤을 만끽할 수 있습니다.", cityList.get(5)));

        //화성
            //피닉스 테라스
        hotelRepository.save(new Hotel("피닉스 리버스 호텔",5L,"재생과 변화의 아름다움을 상징하며, 감동적인 숙박을 제공합니다.", cityList.get(2)));
            //아르곤
        hotelRepository.save(new Hotel("인페르노 타워", 4L, "아름다운 붉은 황혼을 감상할 수 있는 최고층 레스토랑과 함께하는 호텔로, 화성의 열정을 느낄 수 있는 곳입니다.", cityList.get(0)));
            //큐리오시티
        hotelRepository.save(new Hotel("붉은 사막 오아시스", 5L, "화성의 붉은 사막 한가운데 위치한 오아시스 같은 호텔로, 화성의 거친 자연을 안락함 속에서 체험할 수 있습니다.", cityList.get(1)));
        hotelRepository.save(new Hotel("스페이스 오아시스 호텔", 3L, "인류 역사상 처음으로 지어진 식민지 행성 호텔의 역사를 가지고 있다. 편안하고 현대적인 4성급 호텔로써, 다양한 레스토랑과 시설을 갖추고 있어 여행자들에게 다채로운 선택지를 제공합니다.", cityList.get(1)));


        //금성
            //오리엔타 골드필드
        hotelRepository.save(new Hotel("골든 오아시스 호텔",5L,"화려함과 사치를 대표하는 숙소로, 최고의 호사를 경험할 수 있습니다.", cityList.get(6)));
        hotelRepository.save(new Hotel("골든 선라이즈 스위트", 3L, "오로라 빌라에서 가장 아름다운 금성의 노을과 새벽을 감상할 수 있는 호텔입니다. 사치와 휴식을 동시에 제공합니다.", cityList.get(6)));
            //베너스 블리스
        hotelRepository.save(new Hotel("블리스풀 하벤 호텔",4L,"품격과 안락함을 제공하며, 기억에 남는 숙박을 보장합니다.", cityList.get(7)));


        //천왕성
            //프로스트밸리 도메인
        hotelRepository.save(new Hotel("아이스피크 호텔",5L,"빙하 아치와 빙산의 경이로운 전망을 제공합니다. 여기서는 천왕성의 차가운 아름다움을 따뜻한 안락함 속에서 경험할 수 있습니다.", cityList.get(8)));
            //셀레스티얼 라이트시티
        hotelRepository.save(new Hotel("오로라 뷰 리조트",5L,"천혜의 자연 현상을 객실에서 직접 관람할 수 있는 특별한 장소입니다. 밤이 되면 호텔은 꿈꾸는 듯한 분위기로 변모하며, 방문객들에게 잊을 수 없는 경험을 선사합니다.", cityList.get(9)));
    }

    private void initCity() {

        if (!getPlanetList()) return;


        //화성
        cityRepository.save(new City("아르곤","아름다운 화성 행성의 지역입니다.", planetList.get(2)));
        cityRepository.save(new City("큐리오시티","올림푸스산이 있는 랜드마크 지역입니다.", planetList.get(2)));
        cityRepository.save(new City("피닉스 테라스","불사조의 전설에서 영감을 받은 지역으로, 불과 재생의 상징적인 장소입니다.", planetList.get(2)));

        //달
        cityRepository.save(new City("루나리아","달빛이 은은하게 비치는 고대 궁전과 정원들이 펼쳐진 지역으로, 마법 같은 분위기를 자아내는 곳입니다.",planetList.get(1)));
        cityRepository.save(new City("세레나티 베일","평화롭고 고요한 분위기의 대자연 속에 위치한 지역으로, 달의 아름다움을 가장 잘 느낄 수 있는 곳입니다",planetList.get(1)));
        cityRepository.save(new City("아르테미스 플레인","달의 여신 아르테미스에게 바친 광활한 평원 지역으로, 탐험과 모험을 즐기는 이들을 위한 장소입니다. ",planetList.get(1)));

        //금성
        cityRepository.save(new City("오리엔타 골드필드", "금성의 중심부에 위치한 오리엔타 골드필드는 그 이름처럼 황금으로 빛나는 광활한 들판을 자랑합니다. 태양의 빛이 가장 진하게 내리쬐는 이 곳은, 사치와 풍요로움의 상징으로 여겨지며, 황금색 꽃이 만발한 정원들과 금빛 모래가 펼쳐진 아름다운 풍경으로 유명합니다.", planetList.get(0)));
        cityRepository.save(new City("베너스 블리스", "금성의 서쪽 끝에 자리 잡은 사치스럽고 화려한 건축물이 특징인 도시입니다. 방문객들에게 금성만의 독특한 아름다움과 환상적인 경험을 선사합니다. 베너스 블리스의 고급스러운 시장과 레스토랑에서는 금성의 미식을 만끽할 수 있으며, 도시 곳곳에서 열리는 축제와 행사는 방문객들에게 잊지 못할 추억을 선사합니다.", planetList.get(0)));


        //천왕성
        cityRepository.save(new City("프로스트밸리","이 얼음의 왕국은 천왕성에서 가장 아름다운 빙하와 얼음 협곡이 펼쳐져 있는 곳입니다. 영원한 눈과 얼음으로 둘러싸인 프로스트밸리 도메인은 천왕성의 가장 차갑지만, 동시에 가장 환상적인 자연 경관을 자랑합니다.", planetList.get(3)));
        cityRepository.save(new City("셀레스티얼 라이트시티","천왕성의 폴라 라이트, 즉 북극광이 가장 잘 보이는 지역입니다. 얼음 위에 세워진 이 도시는 밤하늘을 수놓는 빛의 축제로 유명합니다. ", planetList.get(3)));

    }



    private void initCategory() {
        categoryRepository.save(new Category("금성"));
        categoryRepository.save(new Category("달"));
        categoryRepository.save(new Category("화성"));
        categoryRepository.save(new Category("천왕성"));
    }



    private boolean getPlanetList() {
        planetList.addAll(planetRepository.findAll());

        if(planetList.isEmpty()){
            log.error("[Not found planetList:]");
            return false;
        }
        return true;
    }
    private boolean getCityList() {
        cityList.addAll(cityRepository.findAll());

        if(cityList.isEmpty()){
            log.error("[Not found cityList:]");
            return false;
        }
        return true;
    }

    private boolean getSpaceShipList() {
        spaceShipList.addAll(spaceShipRepository.findAll());

        if(spaceShipList.isEmpty()){
            log.error("[spaceShipList.isEmpty:]");
            return false;
        }
        return true;
    }
}
