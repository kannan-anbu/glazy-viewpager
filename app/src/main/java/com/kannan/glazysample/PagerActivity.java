package com.kannan.glazysample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.Utils;
import com.kannan.glazy.pager.GlazyFragmentPagerAdapter;
import com.kannan.glazy.pager.GlazyViewPager;
import com.kannan.glazy.transformers.GlazyPagerTransformer;
import com.kannan.glazy.views.GlazyImageView.ImageCutType;

public class PagerActivity extends AppCompatActivity {

    private GlazyViewPager mPager;
    private GlazyFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pager);
        setTitle("GlazyViewPager");

        mPager = (GlazyViewPager) findViewById(R.id.pager);
//        mPager.setOnPageChangeListener(
//                new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                    }
//
//                    @Override
//                    public void onPageSelected(int position) {
//
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int state) {
//                        if (state != ViewPager.SCROLL_STATE_IDLE) {
//                            final int childCount = mPager.getChildCount();
//                            for (int i = 0; i < childCount; i++)
//                                mPager.getChildAt(i).setLayerType(View.LAYER_TYPE_NONE, null);
//                        }
//                    }
//                }
//        );
        mPagerAdapter = new GlazyFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        Resources resources = getApplicationContext().getResources();
        int img_matt = resources.getIdentifier("matt_le_blanc", "drawable", getPackageName());
        int img_courteny = resources.getIdentifier("courteny_cox", "drawable", getPackageName());
        int img_david = resources.getIdentifier("david_schwimmer", "drawable", getPackageName());
        int img_jennifer = resources.getIdentifier("jennifer_aniston", "drawable", getPackageName());
        int img_lisa = resources.getIdentifier("lisa_kudrow", "drawable", getPackageName());
        int img_matthew = resources.getIdentifier("matthew_perry", "drawable", getPackageName());

        String title = "Title Text".toUpperCase();
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                " eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute" +
                " irure dolor in reprehenderit in voluptate velit esse cillum " +
                "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String desc_jenif = "Jennifer Joanna Aniston (born February 11, 1969)is an American" +
                " actress, producer, and businesswoman. She is the daughter of Greek-born actor" +
                " John Aniston and American actress Nancy Dow. Aniston gained worldwide recognition" +
                " for portraying Rachel Green on the popular television sitcom Friends (1994–2004)," +
                " a role which earned her a Primetime Emmy Award, a Golden Globe Award, and a" +
                " Screen Actors Guild Award.";
        String desc_david = "Born in New York and raised in Los Angeles, Schwimmer was" +
                " encouraged by a high school instructor to attend a summer program in " +
                "acting at Northwestern University. Inspired by that experience, he returned " +
                "to Northwestern where he received a bachelor's degree in speech/theater." +
                " In 1988, along with seven other Northwestern graduates, he co-founded " +
                "Chicago's Lookingglass Theatre Company.";
        String desc_lisa = "Lisa Valerie Kudrow; born July 30, 1963 is an American actress," +
                " comedian, writer and producer. She gained worldwide recognition for her" +
                " ten-season run as Phoebe Buffay and Ursula Buffay on the television sitcom " +
                "Friends, for which she received many accolades, including six Emmy Award " +
                "nominations, winning once in 1998, and twelve Screen Actors Guild Award " +
                "nominations, winning twice in 1996 and 2000.";
        String desc_matt = "Matthew Steven LeBlanc (born July 25, 1967) is an American actor," +
                " comedian, television host, and producer, best known for his role as the " +
                "dim-witted womanizing actor Joey Tribbiani on the popular NBC sitcom Friends," +
                " which ran from 1994 to 2004. LeBlanc also stars as a fictional version of " +
                "himself in the BBC/Showtime comedy series Episodes. He won a Golden Globe award" +
                " for his work on Episodes, and was nominated for an Emmy three times for his " +
                "work on Friends and four times for Episodes. Since 2016, LeBlanc has hosted the" +
                " BBC motoring show Top Gear.";
        String desc_corteny = "Courteney Bass Cox (born June 15, 1964) is an American actress," +
                " producer, and director. She is best known for her roles as Monica Geller on " +
                "the NBC sitcom Friends, Gale Weathers in the horror series Scream, and Jules" +
                " Cobb in the ABC/TBS sitcom Cougar Town, for which she earned her first Golden" +
                " Globe nomination. Cox also starred in the FX series Dirt. She owns a production" +
                " company, called Coquette Productions, which was created by Cox and her " +
                "then-husband David Arquette. Cox also worked as a director on her sitcom Cougar" +
                " Town and the television film Talhotblond.";
        String desc_matthew = "Matthew Langford Perry (born August 19, 1969) is a Canadian and " +
                "American actor. Perry is best known for his role as Chandler Bing on the " +
                "long-running NBC television sitcom Friends, as well as his portrayal of Ron " +
                "Clark in the 2006 television movie The Ron Clark Story. Along with starring " +
                "in the short-lived television series Studio 60 on the Sunset Strip, Perry has " +
                "appeared in a number of films, including Fools Rush In (1997), The Whole Nine" +
                " Yards (2000), and 17 Again (2009). In 2010, he expanded his résumé to include " +
                "both video games and voiceover work when he voiced Benny in the role-playing " +
                "game Fallout: New Vegas.";

        mPagerAdapter.addCardItem(
                new GlazyCard()
                        .withTitle("MATT LE BLANC")
                        .withSubTitle("ACTOR")
                        .withDescription(desc_matt.toUpperCase())
                        .withImageRes(img_matt)
                        .withImageCutType(ImageCutType.WAVE)
                        .withImageCutCount(3)
                        .withImageCutHeight(80)
        );
        mPagerAdapter.addCardItem(
                new GlazyCard()
                        .withTitle("COURTENY COX")
                        .withSubTitle("ACTRESS")
                        .withDescription(desc_corteny.toUpperCase())
                        .withImageRes(img_courteny)
                        .withImageCutType(ImageCutType.LINE_POSITIVE)
                        .withImageCutCount(3)
                        .withImageCutHeight(80)
        );
        mPagerAdapter.addCardItem(
                new GlazyCard()
                        .withTitle("DAVID SCHWIMMER")
                        .withSubTitle("ACTOR")
                        .withDescription(desc_david.toUpperCase())
                        .withImageRes(img_david)
                        .withImageCutType(ImageCutType.ARC)
                        .withImageCutCount(3)
                        .withImageCutHeight(80)
        );
        mPagerAdapter.addCardItem(
                new GlazyCard()
                        .withTitle("JENNIFER ANISTON")
                        .withSubTitle("ACTRESS")
                        .withDescription(desc_jenif.toUpperCase())
                        .withImageRes(img_jennifer)
                        .withImageCutType(ImageCutType.LINE_POSITIVE)
                        .withImageCutCount(3)
                        .withImageCutHeight(80)
        );
        mPagerAdapter.addCardItem(
                new GlazyCard()
                        .withTitle("LISA KUDROW")
                        .withSubTitle("ACTRESS")
                        .withDescription(desc_lisa.toUpperCase())
                        .withImageRes(img_lisa)
                        .withImageCutType(ImageCutType.WAVE)
                        .withImageCutCount(3)
                        .withImageCutHeight(80)
        );
        mPagerAdapter.addCardItem(
                new GlazyCard()
                        .withTitle("MATHEW PERRY")
                        .withSubTitle("ACTOR")
                        .withDescription(desc_matthew.toUpperCase())
                        .withImageRes(img_matthew)
                        .withImageCutType(ImageCutType.ARC)
                        .withImageCutCount(3)
                        .withImageCutHeight(80)
        );


        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        mPager.setClipToPadding(false);
        mPager.setPageMargin(Utils.dpToPx(getApplicationContext(), 25));

        mPager.setPageTransformer(false,
                new GlazyPagerTransformer(
                        GlazyPagerTransformer.TransformType.ZOOM)

        );
    }

}




//    compile 'com.android.support:design:25.1.0'
//            compile 'com.android.support:recyclerview-v7:25.1.0'
//            compile 'com.android.support:cardview-v7:25.1.0'
//            compile 'com.github.bumptech.glide:glide:3.6.1'
