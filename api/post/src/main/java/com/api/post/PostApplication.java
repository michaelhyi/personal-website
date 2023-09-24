package com.api.post;

import com.api.post.dto.PostCreateRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PostApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PostService service) {
        return args -> {
            PostCreateRequest req = new PostCreateRequest("Ants From Up There by Black Country, New Road", new Date(1676703600000L), "In their sophomore LP, Black Country, New Road coalesces elements of\n" +
                    "            post-rock and chamber pop to deliver an emotional, metaphorical\n" +
                    "            oxymoron consisting of superlative songwriting and vehement\n" +
                    "            storytelling.\n\nDays before the release of this piece, Black Country, New Road made an announcement that unequivocally altered the course of the band’s future. Isaac Wood, lead singer and frontman of Black Country, New Road, had decided to leave the band due to mental health and personal concerns. For an auspicious yet budding musical group, the news was devastating to the fanbase who’d been there since the beginning. However, Isaac’s departure makes <i>Ants From Up There</i> all the more an essential moment in contemporary rock music.<br/><br/>I remember listening to the band's first project, titled <i>For the first time</i>, for the first time (haha). I was intrigued by the attention and critical acclaim the album received, but was slightly disappointed by its lacking presentation of a cohesive storyline. It was, nonetheless, a great record with promising instrumental ideas and post-rock concepts, but <i>For the first time</i> wasn’t as substantial in its overall message. <i>Ants From Up There</i> defines that distinction.<br/><br/>The album explores the theme of unrequited love through a story in which Isaac embarks on a journey of anguish and heartbreak over a former lover. That former lover is metaphorically compared to a Concorde jet, and at the altitudes of Concorde, people down below look like mere ants – hence the title <i>Ants From Up There</i>.<br/><br/>I like to call this record an oxymoron because of how sharp some of its juxtapositions are. For instance, had you played “Chaos Space Marine” and “Basketball Shoes” side-by-side, I would’ve assumed that these were separate works. However, it’s this album’s range, ambiguity, and depth that make it a standout project in the modern post-rock≈and chamber pop scene.<br/><br/>Many call <i>Ants From Up There</i> the best album of 2022. Some call it overrated and inessential prose. In this opinion piece, I hope to explore the multifaceted nature of this record, breaking down each track and its contribution to the overall storytelling capabilities of the band.<br/><br/>The record opens with a brief 54-second instrumental titled “Intro,” repeating an arpeggiating riff that serves as an instrumental motif throughout the album. This overture begins with perhaps one or two wind instruments before gradually accumulating its instrumental palette – with the addition of strings, a bassline, and drums – and coming to a sudden halt. In a way, this opening foreshadows how the LP will end. “Intro” and the closing track “Basketball Shoes” are similarly structured as a crescendo; albeit prolonged, “Basketball Shoes,” like “Intro,” emerges subtly with a mellow tone and gradually piles volume and intent before sharply cutting off at its culmination.<br/><br/>Following “Intro” is “Chaos Space Marine,” which is a track that I’d recommend to people who are here to enjoy Black Country, New Road for its music rather than its narrative. At a slightly slower tempo than its preceding instrumental, “Chaos Space Marine” repeats the main riff of “Intro” but transforms it into a complete phrase. Hilariously, I’ve heard some refer to the track’s opening as the soundtrack to a Tom and Jerry chase. With its energetic and charismatic tone, “Chaos Space Marine” is the most “catchy” and fast-paced track in this entire album. Its spirited employment of strings and uplifting vocals disguises the despondence and affliction of the record with jubilance and exhilaration – thus misleading the listener and initiating the aforementioned oxymoron.<br/><br/>The next track, “Concorde,” is easily my second favorite track in the entire project. As its title suggests, it’s the most vivid integration of the LP’s primary motif, that being a Concorde jet. To put it in context, the Concorde was a supersonic airliner turned diplomatic embarrassment as the United Kingdom and France refused to abandon its production despite tremendous financial losses. Subsequently, Isaac develops the Concorde as one of the most impressive metaphorical amalgamations that I’ve witnessed with music. In essence, he addresses his former lover as Concorde, but despite her disregard for his infatuation, he refuses to let go. This metaphor, paralleling the UK and France’s tenacity, establishes the first rendition of the record's theme of unrequited love.<br/><br/>This song begins with a subtle opening riff followed by the soft, almost uncertain, vocals of Isaac, beginning his story with Concorde. “Concorde” is the first instance in this record where Black Country, New Road prioritizes reminiscent narration over melodic fulfillment. Consequently, Isaac resorts to reciting prose, almost turning “Concorde” into a spoken word with instrumental accompaniment. To that regard, “Concorde” is the pinnacle of Black Country, New Road’s songwriting, especially in its chorus.<br/><br/><div style=\"font-weight:300; font-style:italic; text-align: center; display: flex; flex-direction: column; align-items: center;\">And you, like Concorde<br/>I came, a gentle hill racer<br/>I was breathless<br/>Up on every mountain<br/>Just to look for your light<br/><br/>But for less than a moment<br/>We'd share the same sky<br/>And then Isaac will suffer<br/>Concorde will fly<br/><br/></div>\n" +
                    "The chorus illustrates the depth of Isaac’s infatuation. Out of desperation, Isaac takes tremendous effort, risk, and sacrifice – in a metaphorical sense, scaling mountains breathlessly – to get a momentary glimpse of Concorde as it flies away. Following each iteration of the chorus comes a gratifying instrumental phrase, which in its own way is bittersweet. It’s quite simple. It consists of a guitar line with some percussion, but underneath its simplicity, it’s pure. The sounds mesh to create a charming dynamic of instruments that allows them to communicate while simultaneously giving the track a chance to breathe. After “Concorde will fly,” the song unravels, dissecting itself to merely its bassline and gradually adding some instruments; we’re at the final breaths before the great climax.<br/><br/><div style=\"font-weight:300; font-style:italic; text-align: center; display: flex; flex-direction: column; align-items: center;\">Concorde, we’re old now<br/>…<br/>So don't tell me you're hungry<br/>'Cause darling, I'm starving myself<br/>And I heard you're on Atkins as well</div><br/><br/>In spite of all the years past, Isaac’s infatuation for Concorde lingers. However, chasing Concorde remains demanding. As depicted through the metaphor of a “starving” Isaac, Concorde is oblivious to her detrimental impacts on Isaac and stays “hungry” – even while being on an “Atkins” diet. In the final moments prior to its end, “Concorde” begins to build through saxophone trills and a violin tremolo as Isaac loses over his vocal dynamics. Delivering the final blow with “And Concorde and I, Die free this time,” the band surrenders into the track’s cathartic coda. It’s loud, grand, and visceral, yet wistful and purifying. Releasing all its tension, the breadth of instruments at the song’s climax soon fades away.<br/><br/>In its wake, “Concorde” transitions into “Bread Song,” which continues the theme of a faltering relationship. However, compared to “Concorde,” “Bread Song” is a weaker exploration of the album’s concepts. It explicates the idea of bread crumbs falling into one’s bed, representing the falling crumbs of a relationship. With a far greater emphasis on timbre and texture, the verses of “Bread Song” are also more of a prosaic spoken word similar to those of “Concorde” but are far more bleak and dismal. To me, it’s slightly unsettling to listen to. Nonetheless, it’s a great track, but not as consequential to the LP as some of its others.<br/><br/>Following “Bread Song” is a track that is arguably more joyous than “Chaos Space Marine:” “Good Will Hunting.” When straddled by tracks like “Bread Song” and “Haldern,” “Good Will Hunting” does feel oddly out of place. Its frenzied melody and upbeat riffs, however, continue this oxymoronic style of masking the pessimistic nature of the song as it continues the story of a codependent relationship. As “Good Will Hunting” comes to a sudden halt, it is replaced by “Haldern,” which reminds me of post-rock works similar to those of bands like Godspeed You! Black Emperor. Isaac returns to his soft, whisper-like vocals while a guitar, piano, and violin slowly wilt away. Gradually, Isaac reveals how vulnerable he can get in telling his story of finding a partner that is there to comfort and protect him. As “Haldern” concludes, a saxophone and piano both ascend in unison, soon followed by the weeping violin.<br/><br/>“Mark’s Theme” is a moment in the album that’s underappreciated by most listeners. It’s an instrumental prelude, and like “Intro,” it precedes what I consider the two halves of this LP. Opening with a saxophone cadenza, it slowly evolves into a sweet piece of free-flowing piano, saxophone, and violin as a tribute to the band’s saxophonist’s late uncle, who greatly supported the band. I wholeheartedly enjoy it for what it entails melodically as well as its role as an emblem of family, which I’ll explore with the following and my favorite track: “The Place Where He Inserted The Blade.” <br/><br/>…<br/><br/>Now, we’re at the latter half of the record, which is only composed of the final 3 tracks. Starting with this 7-minute epic, “The Place Where He Inserted The Blade” is by far my favorite on this album. It opens with piano octaves immersed in its somber connotations before untangling into moving notes. As the piano phrases shift, the piece’s main guitar chord progression is revealed while a flute reveals the song’s motif at its slow tempo. Returning to his hesitant and vulnerable state, Isaac delicately seeks satisfaction through childhood memories and nostalgia:<br/><br/><div style=\"font-weight:300; font-style:italic; text-align: center; display: flex; flex-direction: column; align-items: center;\">It takes a few months, but our bones heal<br/>We’re stronger, and we tell all our school friends<br/>And they sign our cast in the playground<br/><br/></div>\n" +
                    "Subsequently, the track begins moving. The instruments awake from their lulled state, accumulating adrenaline for the chorus while Isaac hints at his vulnerability and lack of control over his emotions. One particular recurring motif in this track is sleep, in which an imaginary sense of fulfilled love comes to Isaac towards a significant other in his dreams. However, such contentment is immediately stripped away with the delivery of “Good morning,” initiating the chorus and waking Isaac from the illusions of his dreams. <br/><br/>By now, Isaac’s vocals start to flutter, crushed by the realization that it was all in his head. As the piano, flutes, bass, and drums cultivate a bittersweet and poignant melody, Isaac embarks on his journey through disillusionment. During the chorus, Black Country, New Road truly displays the eloquence and beauty with which they deliver the piano. The piano, without a doubt, is an underappreciated aptitude of not just this track but the band as a whole. As the chorus steadily diminishes, the track, in a way, resets. We’re back to the gloomy, sedative flute melodies and moving piano notes, giving the song, as well as Isaac, a moment to recollect and think. As the second verse arrives, both the song and Isaac open restlessly; they accelerate much sooner and rather uncontrollably. Isaac has reached a point of pure desolation and devastation, delivering the verse in his heart-wrenching voice. By the second iteration of the pre-chorus, the instruments are no longer so hypnotic but completely roused, and with the arrival of the last chorus comes my favorite moment on this entire album.<br/><br/>The ultimate “Good morning,” signaling the start of the song’s end, is sung not just by Isaac but everyone. To me, it’s the most compelling moment of this LP. The entire band, in unison, delivers it mournfully; it’s powerful. The saxophone is now grieving over the looming piano and violin, and Isaac is crying out of pure heartbreak and emotion. However, what completes this chorus is the backing of the band’s vocals. The “ba-da, ba-da, bum bum” is particularly touching, harmonized underneath the melancholy and darkness of the piece.<br/><br/><div style=\"font-weight:300; font-style:italic; text-align: center; display: flex; flex-direction: column; align-items: center;\">Show me the fifth or the cadence you want me to play<br/>Good morning<br/>Show me where to tie the other end of this chain<br/><br/></div>Alas, we’ve arrived at my favorite lines in the entire album, which, once again, explore the idea of one-sided, unreciprocated affection. But this time, that affection is with the listener. Isaac, like any musician, provides music out of pure affection and care for his audience, which is us. But Isaac asks us: what do you provide in return? It’s a question that we may never be able to answer, given Isaac’s departure from the band. He lacks direction, something that only we can provide. As Isaac cries these lyrics in pain, he joins the rest of the band harmonizing over the “ba-da, ba-da, bum bum” until the guitar and piano slowly lead the way toward the end.<br/><br/>It’s beautiful. It’s so beautiful. It reminds me of the ending to The Beatles’ “Hey Jude,” in which the Fab Four all sing the “Na na na na” together at the end. It’s sweet, symbolizing unity and friendship. Especially in the <a href=”https://youtu.be/A_MjCqQoLLA?t=240” target=\"_blank\" rel=\"noreferrer\">\"Hey Jude” music video</a>, we can watch with joy the audience joining the Beatles in singing the song’s sweet ending. Likewise, it’s an authentic and genuine moment of catharsis for the band. There’s even a <a href=”https://www.youtube.com/watch?v=90i4yP0-yQ0” target=\"_blank\" rel=\"noreferrer\">video</a> of them recording this song’s conclusion. It's moments like these where music brings us together and provides something to bond over. It’s why I love music; it’s complex. Through writing, production, and storytelling, music always delivers as a proxy for intimacy.<br/><br/>But most importantly, this moment is especially appealing to me because my greatest value is family (this is where I digress a bit). I’d like to circle back to the emblem of family that I mentioned in “Mark’s Theme.” Family has always been the intrinsic motivation for everything I do, and my greatest dream is to hold a framed photo with my wife and kids smiling back at me. It’s a reflection of who you are as a person, and moments like these in music, where bands are complete and together, are why they stand out to me. Perhaps for Black Country, New Road, their “family” moment was fleeting, given that Isaac left shortly before this album’s release. Perhaps for Black Country, New road, their moment of unity is over despondency rather than bliss like The Beatles. But by the end of it all, it’s always something worth smiling at when you listen.<br/><br/>…<br/><br/>In the midst of the silence, a guitar gently strums individual notes like falling, dragging raindrops. To that regard, picture a snow globe for a moment. With its glass dome, you must treat it with care; they’re meant to be enveloped and cradled. If you provide it with a soft shake, you’d watch in awe of the flurry of miniature snowflakes, creating a microcosm of a beautiful winter. However, “Snow Globes” is another extension of the emotional oxymoron that this album encompasses. Despite the tender connotations of its title, “Snow Globes” instead presents the most chaotic moments in the LP. It introduces the track with a relaxed instrumental intro, spanning 3 minutes with progressive instrumental additions and dynamic escalation. Then, Isaac delivers the primary verse, revisiting his soft, whisper-like vocals. This musical structure is a recurring idea throughout the album, one in which instruments steadily build on one another while Isaac narrates with soft-spoken vocals – the calm before the storm. But this time, throughout the verse, drums flutter in the background almost uncontrollably, like thunder foreshadowing a major blizzard that will plague the serenity of the snow globe.<br/><br/><div style=\"font-weight:300; font-style:italic; text-align: center; display: flex; flex-direction: column; align-items: center;\">Oh, god of weather, Henry knows<br/>Snow globes don't shake on their own, ah<br/><br/></div>The chorus of “Snow Globes” is unequivocally repetitive. The above lines, although highlighting the band’s profound songwriting, are repeated continuously while the violin mourns through the violent clashing of the drums – representing the arrival of the blizzard caused by the ruthless shaking of the snow globe. As the drums swell intensely and the sheer volume it reaches is extraordinarily harsh, the drums completely overpower Isaac and force him to contort his voice into an agonizing scream. Some have even argued that this is poor sound mixing on the band’s part, and in conjunction with the song’s reiterative nature, I’ve heard some conclusions that “Snow Globes” is nothing more than an uninteresting and pretentious track. However, underneath its entropy, it’s an interesting application of lulling the listener into hypnosis before crescendoing dramatically into a culmination of rage and despair. Ultimately, when the storm ends, each instrument individually fades into rest while Isaac quietly recites the above lines – injecting the sedative to the song.<br/><br/>We have now arrived at Black Country, New Road’s magnum opus. The nearly 13-minute closing track to this LP, titled “Basketball Shoes,” is one that contains all of the album’s motifs, concepts, and ideas in one. Serving as the foundation for the entire album itself, “Basketball Shoes” has been a fan favorite during live shows for years, and with the release of <i>Ants From Up There</i>, this version is the long-awaited and only studio recording.<br/><br/>This song is divided into three parts, with the third one being my favorite and, in my opinion, playing the most consequential role in concluding the album as a whole. The first part sustains the same structural idea seen prior in the album: the progressive buildup of instruments with Isaac’s fragile narration. Composing the track’s first 5 minutes and a half, this first part comes to a swift end with an accelerando of violin and guitars followed by an upbeat arpeggiating guitar riff. Part 2 of “Basketball Shoes” is one of my favorite moments from the album. It’s fun, catchy, and energetic, and it has that indie-pop atmosphere to it. As the shortest part of the track, it quickly lulls back into the slow, gradual, and intentional instrumental progression – a controlled alternation between bass, guitar, violin, and winds between each beat. However, this time, the band reveals a grand fortissimo, with a breadth of instruments and choir releasing the album’s emotion, anguish, and envy, foreshadowing the final coda to the album with the arrival of Part 3.<br/><br/>Part 3 is a close second for my favorite moment on this album. Beginning, once again, with the gradual progression of instruments, a distressed Isaac croons over the album’s final guitar riff and a dismal saxophone cries like it's a funeral. Then, Isaac conducts the final exploration of the album’s theme of one-sided love with arguably the most compelling line in modern music performance:<br/><br/><div style=\"font-weight:300; font-style:italic; text-align: center; display: flex; flex-direction: column; align-items: center;\">Oh, your generous loan to me, your crippling interest<br/><br/></div>Perhaps this is Isaac exploring a codependent relationship with Concorde again, or perhaps another message to the listener, repeating a similar idea scrutinized in “The Place Where He Inserted The Blade.” The “generous loan” could refer to the time and effort that Isaac received from his former lover while her “crippling interest” could be the heartbreak that lingers from the crumbling relationship. On the other hand, the “generous loan” could be his audience lending their time and effort to listen to Isaac’s music while the “crippling interest” is the adverse impacts on his mental health while trying to keep up with his fanbase. Albeit both valid interpretations, I personally believe that the former is far more plausible given that “Basketball Shoes” has been performed long before the fame the band has accumulated up to this point.<br/><br/>This line is iterated three times across the final coda of this piece. After the first iteration, the band returns to the previous fortissimo presented during the interlude between Parts 2 and 3, with blaring distortion of the electric guitars and twisted vocal delivery from Isaac. What comes after the second iteration is one of the prettiest moments in the album. Every instrument interacts with one another at its fullest, climactic volume while backed by the harmonizing choir – reminiscing of the unified band at the ending of “The Place Where He Inserted The Blade” and the virtue of family. At the peak of the crescendo, Issac returns, screaming the lyrics out of pure agony and catharsis. As we arrive at the third and final iteration of the above line, which is the last line performed in the entire record, every instrument thrashes beautifully, repeating the motifs of “Intro” while gradually breaking down from the ultimate culmination. With the shriek of the saxophone, everything comes to a final halt.<br/><br/>That’s it. The end. <br/><br/>Perhaps to Concorde, we’re all insignificant. We’re nothing but mere ants, and Issac happened to be one ant amongst colonies, which drove him over the boundaries of stability and forced his departure from such a promising band. <i>Ants From Up There</i> is such a rare moment in music, one in which we can witness a man like Isaac deteriorate; we may never see these pieces performed live. It embodies powerful emotions and explores thought-provoking themes, and it’s important to me in its interpretation of love, family, and friendship and the fear that lurks underneath it all.<br/><br/>That being said, the music presented by Black Country, New Road is far from one-dimensional. The story of Isaac is tragic, but it's moments like this record – one that transcends the contemporary rock genre through ardent storytelling and visceral instrumental camaraderie – that make his story worth telling.");
            service.createPost(req);
        };
    }
}