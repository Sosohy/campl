package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<PostDTO> postExample = new ArrayList<>();
    public static ArrayList<PlaceDTO> placeExample = new ArrayList<>();
    public static ArrayList<PostDTO> myPostExample = new ArrayList<>();
    public static ArrayList<PostDTO> bookmarkExample = new ArrayList<>();

    private static int user = -1;
    public static UserDTO userObj = null;
    public static Retrofit retrofit;
    public static CamplAPI camplAPI;

    TabLayout tabs;
    Fragment selected;

    Fragment homeFrag, writingFrag, myPageFrag;

    public static final int[] tabIcon = {R.drawable.home_1, R.drawable.write, R.drawable.mypage};
    public static final int[] tabClickIcon = {R.drawable.home, R.drawable.photo, R.drawable.mypage_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder().setLenient().create();

        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        retrofit = new Retrofit.Builder().client(client).baseUrl(camplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        camplAPI = retrofit.create(CamplAPI.class);

        tabs = (TabLayout) findViewById(R.id.tabLayout);

        homeFrag = new HomeFragment();
        writingFrag = new WritingFragment();
        myPageFrag = new MyPageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, myPageFrag).commit();

        setCustomTabs();
        setClickIcon(2);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if(position != 1)
                    setCustomTabs();
                selected = null;
                if (position == 0) {
                    if (MainActivity.getUser() == -1){
                        Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                        selected = myPageFrag;
                        setClickIcon(2);
                    }
                    else{
                        selected = homeFrag;
                        setClickIcon(position);
                    }
                } else if (position == 2) {
                    selected = myPageFrag;
                    setClickIcon(position);
                }

                if (position == 1) {
                    if (MainActivity.getUser() == -1) {
                        Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                        startActivity(intent);
                    }
                } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                    startActivity(intent);
                } else {
                    setCustomTabs();
                    int position = tab.getPosition();

                    if (position == 0) {
                        if (MainActivity.getUser() == -1){
                            Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                            selected = myPageFrag;
                            setClickIcon(2);
                        }
                        else{
                            selected = homeFrag;
                            setClickIcon(position);
                        }
                    } else if (position == 2) {
                        selected = myPageFrag;
                        setClickIcon(position);
                    }
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
                    }
                }
        });
    }


    private void setCustomTabs() {
        for (int i = 0; i < tabIcon.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.customtab,null);
            TabLayout.Tab tab = tabs.getTabAt(i);
            view.findViewById(R.id.tab_icon).setBackgroundResource(tabIcon[i]);
            if(tab!=null){
                tab.setCustomView(null);
                tab.setCustomView(view);
            }
        }
    }

    void setClickIcon(int clickIcon){
        View view = getLayoutInflater().inflate(R.layout.customtab,null);
        TabLayout.Tab t = tabs.getTabAt(clickIcon);
        view.findViewById(R.id.tab_icon).setBackgroundResource(tabClickIcon[clickIcon]);
        tabs.getTabAt(clickIcon).setCustomView(null);
        tabs.getTabAt(clickIcon).setCustomView(view);
    }


    public static int getUser() {
        return user;
    }

    public static void setUser(int user) {
        MainActivity.user = user;
    }

  /*  void createExPost() {
        //POST EXAMPLE CODE 추후에 지우기
        postExample.add(new PostDTO("2시간 안에 누구보다 재밌게 노는 법", new UserDTO(), false, false, 0, new String[]{"https://mblogthumb-phinf.pstatic.net/MjAxOTAxMjRfOSAg/MDAxNTQ4MzI3NTM4ODI3.Ao8zEog1VR138wbRk0HCseJ-txSrgqssijxPOmKQCxwg.ATqp0s08jvi2mmYZ1ZdnhmItwlgS9ijpkRLIepv73G4g.JPEG.seodaram/IMG_6915.jpg?type=w800", "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/20210528073409512_photo_931052eaee51.jpg"}, "개강 첫주에 OT가 일찍 끝나거나, 동아리 가기 전에 시간이 애매할 때 제가 자주 이용하는 루틴입니다\n" +
                "일단 인도이웃에 가서 치킨카츠카레나 히레카츠카레를 먹어요 그리고 본크레페에 가서 크레페 하나를 먹어주면 2시간은 훌쩍 가더라고요 인도이웃은 음식이 금방 나오는 편인데 본크레페는 항상 사람이 많아서 줄서는시간이 좀 있어요 사장님 혼자 일하시다보니까 더 오래 걸리는 것 같아요 하지만 기다리는 보람이 있는 맛이에요!!! 이러면 2시간 훌쩍 가더라구요\n" +
                "\n" +
                "학교밖으로 나가기 귀찮을 때는 학식 먹고 도서관에 놀러가기도 해요 가을에는 도서관 뒷쪽에 텐트 쳐져 있어서 캠핑 온 기분도 낼 수 있어요!", "BETWEEN1_3", "BETWEEN1_2", "EMPTY_LECTURE", new String[]{"none"}));

        postExample.add(new PostDTO("우주공강 순삭하기", new UserDTO(), false, false, 0, new String[]{"https://t1.daumcdn.net/cfile/tistory/2469AB4D590AF02225", "https://mblogthumb-phinf.pstatic.net/MjAyMDA4MjRfMjAz/MDAxNTk4MjU0NDk2MzU2.fFtafb0UcIuZlBORy8SksYdcYaWQL-0IawTBZlgskTQg.JbxDLBnuQffO_lHW2tfmfkB5xkUhAQC1cN49tR-993og.JPEG.sonia2033/IMG_3250.jpg?type=w800", "https://t1.daumcdn.net/cfile/blog/256C5A4555C967AF0E"}, "새내기 초반에는 우주공강을 좀 심심하게 보냈었는데, 학교 근처에 볼링장이나 만화카페 같은게 있더라고요\n" +
                "만화는 별로 안좋아해서 공강이 길 때는 동기들이랑 볼링치러 자주 가요 특히 오티할 때는 일찍 끝나서 공강이 엄청 긴데 그때 볼링하면 시간 진짜 순삭이에요 무엇보다 팀 짜서 볼링 치다보면 동기들이랑도 많이 친해질 수 있어서 진짜 추천합니다 커피 내기해도 재밌어요 ㅋㅋ\n" +
                "\n" +
                "개강 첫주에는 항상 팔백집 가서 쫄갈비에 볶음밥까지 먹고, 볼링 치고 시간 남으면 맨케이브 가서 크로플까지 먹고 수업 가면 완벽한 개강이에요!\n", "BETWEEN3_5", "OVER3", "DISMISSAL", new String[]{"none"}));


        PostDTO exLink = new PostDTO("동기들끼리 가기 좋은 술집", new UserDTO(), false, false, 0, new String[]{"https://d2uja84sd90jmv.cloudfront.net/posts/mWmu8xTJy_W001SDntSeMw/s.jpg", "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/300_300_20210717030316569_photo_2f50fd31593a.jpg", "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/pre_20200323125724982_photo_06459d748626.jpg"}, "저는 동기들과 술 마시는 걸 엄청 좋아했는데요!\n" +
                "저와 제 동기들은 모두 통학러라 학교 앞에서 마실 기회가 없어서 비대면 수업 하는 동안 아쉬웠습니다...\n" +
                "이제 오프라인으로 학교 다니는 만큼 동기들과 보낼 시간이 많잖아요 그래서 동기들이랑 가기 정말 좋은 술집 몇군데 공유할게요!\n" +
                "\n" +
                "1.여기는 반듯한잔이라는 술집이에요 저 치즈감자전의 비주얼이 보이시나요? 바삭바삭하고 치즈가 미쳤어요\n" +
                "반듯한잔 갈 때마다 무조건 시키는 메뉴입니다 여기는 술 종류 특히 막걸리 종류가 많아서 막걸리 좋아하시는 분들에게 강력 추천합니다! 외부 사람들한테도 인기 많은 찐핫플이기 때문에 예약하고 가세요!\n" +
                "\n" +
                "2.이 곳은 연리지라는 칵테일 바에요 매장 분위기도 좋고 칵테일 가격도 착하고 기본 안주로 아이스크림을 주는 게 진짜 좋더라고요 안주도 맛있는데 매콤크림스튜가 제일 맛있었어요! 여기는 기본 술도 팔아요!\n" +
                "\n" +
                "3.천막집만한 가성비 술집이 없어요!! 가격에 비해 양이 진짜 많아서 처음 갔을 때 엄청 놀랐던 기억이 있어요. 포장마차 분위기라 겨울이나 여름에 가면 분위기도 좋고 안주도 맛있어서 술이 쭉쭉 들어갑니다...\n" +
                "\n" +
                "\n" +
                "혹시 예약하시려는 분들을 위해서 링크 놓고 갑니다~\n", "BETWEEN3_5", "OVER3", "DISMISSAL", new String[]{"none"});
        exLink.setUrls("반듯한잔_https://instagram.com/bandeut.hanjan,연리지_https://instagram.com/yeonridge_sswu,천막집_https://1000mak.modoo.at/?link=aidcnrxd");
        postExample.add(exLink);

        postExample.add(new PostDTO("수업 듣기 전에 밥 먹기 애매할 때 갈 식당", new UserDTO(), false, false, 0, new String[]{"https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/pre_20201023112058561_photo_931052eaee51.jpg", "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTNfMjcz/MDAxNTEzMTY4MzU2NDQ4.Lv-7Rc068mRj5qtREh-3y_ixb-Y1t577tgRdql-wXo8g.nS-l93uki6DnCxpSFgFQZr9FUUDLro7ubOY1kKeNGMIg.JPEG.gome1116/20171211_185531.jpg?type=w800", "https://blog.kakaocdn.net/dn/38kqm/btq8G5TA6So/0TCqePkVfhXdb7ULL23g4k/img.jpg", "https://mblogthumb-phinf.pstatic.net/MjAxOTA5MDlfMjI1/MDAxNTY4MDA5NzU5NDA1.7mGO0-QB0GG7eCbP-PZcGFl3RoHuylusdkqCP2MQgkYg.2g4-iVSvrh2_g-JcVi_FFf348J6WfodCLDUVyDWOr8Ug.JPEG.rayoonmom/20190909_110019.jpg?type=w800", "https://mp-seoul-image-production-s3.mangoplate.com/242355/1042666_1611589789052_3328?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"},
                "3시간 블럭제 특성상 풀강이면 밥먹을 시간이 없어서 진짜 배고프더라고요 그래서 456-789 연강인 날은 한시간 정도 일찍 도착해서 밥먹고 가는데요! 이때 애용하는 곳들을 알려드리겠습니다 ㅎㅎ\n" +
                        "\n" +
                        "<해밀>\n" +
                        "학교 앞에 비싼곳 정말 많은데 여기는 찐가성비맛집이에요 메뉴들도 다 저렴하고 금방금방 나와서 시간 없는데 든든하게 먹고 싶을때 딱입니다 닭갈비볶음밥도 맛있고 치즈제육돌솥밥도 유명해요!\n" +
                        "\n" +
                        "<지지고>\n" +
                        "항상 냄새에 이끌려 들어가게 되는 지지고... 약간 델리만쥬 같기도 해요 맛이 냄새에 못미치는...? 그치만 시간이 정말 없는 시험기간에는 빨리 먹고 치우려고 지지고를 많이 가게 돼요 근데 사람 몰리는 시간에 가면 오래 걸릴 수도 있어요\n" +
                        "\n" +
                        "<그릭데이>\n" +
                        "늦잠자거나 길막혀서 시간이 별로 없을때는 그릭데이에서도 종종 포장해가요 정문 가는 길에 있고 냄새가 많이 안나는게 제일 큰 장점이에요! 저는 기본에 토핑 이것저것 추가해서 먹어요(tmi)\n" +
                        "\n" +
                        "<서브웨이>\u2028서브웨이도 정문 가는 길에 있어서 좋아요! 시험 기간에 밥 먹을 때도 후딱 먹고 치울 수 있어서 지지고만큼 애용합니다... \n" +
                        "\n" +
                        "아무리 시간이 없어도 후식이 빠질 수 없다고 생각해서 제가 자주 가는 카페들도 소개합니다~\n" +
                        "\n" +
                        "<그레이>\u2028아포가토가 제 최애메뉴지만 다른 커피메뉴나 해피버스데이티도 맛있어요! 쿠폰에 도장도 찍을 수 있는데 저는 벌써 4장이나 채웠습니다 ㅎ\n" +
                        "\n" +
                        "<클라츠>\n" +
                        "클라츠도 완전 커피맛집이에요 인테리어부터 커피에 진심이신 것 같은... 라떼메뉴가 엄청 다양한데 피치라떼가 의외로 맛있어요! \n" +
                        "\n" +
                        "<뚜또>\n" +
                        "뚜또는 휘핑크림 혜자로 유명해요 사진은 딸기라떼지만 사실 제 최애는 서인도풍 커피에요!", "UNDER1", "UNDER1", "GOING_TO_SCHOOL", new String[]{"none"}));

        exLink = new PostDTO("슬기로운 공강활용법", new UserDTO(), false, false, 0, new String[]{"https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20191207_25%2F1575726144215c8vMb_JPEG%2Fupload_fc17facad3e4f769dae4b4a9b6c73b9d.jpeg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsdVifmadmB18x90pAUVaN_zHJ3ICPevsATQ&usqp=CAU", "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/300_300_20200317071820773_photo_c863440dd91a.jpg"},
                "성여입 근처에 씨네큐가 있거든요 그래서 공강이 3시간이 넘는 날은 시간이 잘 맞으면 영화를 보러가요 스크린이 작긴 하지만 평일 낮에는 사람이 많지 않아서 쾌적하게 볼 수 있어서 괜찮았어요\n" +
                        "시간이 애매하면 그냥 솔리드웍스 가서 젤라또 하나 사서 성북천 산책해도 좋아요 봄에는 꽃도 많이 펴서 산책로도 예쁘고 고양이들도 종종 보여서 기분도 좋아지고 소소한 추억이 되더라고요!", "BETWEEN1_3", "OVER3", "EMPTY_LECTURE", new String[]{"none"});
        exLink.setUrls("씨네큐 성신여대점 상영시간표_https://www.cineq.co.kr/Theater/Movie?TheaterCode=1002");
        postExample.add(exLink);

        postExample.add(new PostDTO("어색한 동기들과 무조건 친해지는 법", new UserDTO(), false, false, 0, new String[]{"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1SUxyEPhXMi-4xKVrWC1OUcTgmdXRh-g1NA&usqp=CAU", "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/pre_20200107033116038_photo_931052eaee51.jpg", "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/300_300_20211029085230084_photo_2f50fd31593a.jpg"},
               "학창시절 친구가 진짜 친구고 대학 친구는 가짜 친구라는 말이 많은데, 저는 대학와서 찐친들을 만났거든요\n" +
                       "그래서 찐친들과 친해질 수 있던 계기가 되던 날에 했던 것들을 공유하겠습니다!!!\n" +
                       "\n" +
                       "어색한 분위기나 정적을 싫어하는 성격이라 새내기때 학교앞에 뭐가 있는지 공부를 좀 해갔더니 보드게임카페가 있더라고요 동기들이랑 가서 루미큐브 다빈치코드 부루마블 등등 하다보니 시간도 엄청 잘 갔고 어색함도 금방 사라졌어요!\n" +
                       "그리고 친해지는데는 진짜 술만한게 없는것 같아요 도리연이 닭도리탕 맛집인데 소주가 술술 들어갑니다 ㅎㅎㅎ 술마시면 꼭 차가운게 먹고싶잖아요 그래서 아이스크림이나 빙수를 먹는데 설빙도 있지만 일찍 닫아서 카페온더플랜으로 가요 자몽빙수도 맛있고 카카오빙수도 맛있어요!!", "BETWEEN3_5", "OVER3", "DISMISSAL", new String[]{"none"}));


        placeExample.add(new PlaceDTO("https://mp-seoul-image-production-s3.mangoplate.com/1986732_1623955026008918.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80", "카페 피알디", "위치\n" +
                "서울 성북구 동소문로25길 9 휴림빌딩 1층 101호\n" +
                "\n" +
                "영업시간\n" +
                "화~일 12:00~22:00\n" +
                "월요일 휴무\n" +
                "\n" +
                "메뉴\n" +
                "\n" +
                "PRD라떼 6500원\n" +
                "PRD샌드위치 8500원\n" +
                "아인슈페너 5500원\n" +
                "카페피츠 6000원\n" +
                "\n" +
                "PRD / pull revolving door\n" +
                "http://instagram.com/prd.drink"));
        placeExample.add(new PlaceDTO("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLsBL0UKJE09BigjNVgGEy-Ged_tsY-gwxcQ&usqp=CAU","솔리드웍스", "위치\n" +
                "서울 성북구 동소문로24길 35 1층\n" +
                "\n" +
                "영업시간\n" +
                "매일 12:00~22:00\n" +
                "\n" +
                "메뉴\n" +
                "더블컵(2가지맛) 3900원\n" +
                "*젤라또 맛은 매일 변동\n"));
        placeExample.add(new PlaceDTO("https://t1.daumcdn.net/cfile/tistory/2564433D54C3196604", "문화식당", "위치\n" +
                "서울 성북구 보문로30나길 37 지하1층\n" +
                "\n" +
                "영업시간\n" +
                "월~토 12:00~24:00\n" +
                "일 12:00~23:00\n" +
                "브레이크 타임 15:30~17:30\n" +
                "\n" +
                "메뉴\n" +
                "오므라이스 12000원\n" +
                "루꼴라 올리오 13000원\n" +
                "라코타치즈 샐러드 파스타 13000원\n" +
                "삼합 18000원\n" +
                "\n" +
                "문화식당 돈암동\n" +
                "https://instagram.com/munhwabistro"));
    }
   */
}