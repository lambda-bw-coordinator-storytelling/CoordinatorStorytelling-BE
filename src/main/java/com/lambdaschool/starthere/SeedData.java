package com.lambdaschool.starthere;

import com.lambdaschool.starthere.models.Story;
import com.lambdaschool.starthere.models.Role;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.UserRoles;
import com.lambdaschool.starthere.services.RoleService;
import com.lambdaschool.starthere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        roleService.save(r1);
        roleService.save(r2);
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        User u1 = new User("admin", "password","John","Doe","john@doe.com","owner","USA", admins);

        u1.getStories().add(new Story("fake title", "Bolivia", "fakeish description","A creative man is motivated by the desire to achieve, not by the desire to beat others", "fake date",u1));
        u1.getStories().add(new Story("fake title", "Bolivia", "fakeish description","The question isn't who is going to let me; it's who is going to stop me.", "fake date",u1));
        userService.save(u1);

        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), r2));
        User u2 = new User("cinnamon", "1234567","John","Doe","john@doe.com","owner","USA", datas);
        userService.save(u2);

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("user", "password","John","Doe","john@doe.com","owner","USA", users);
        u3.getStories().add(new Story("Grad gets her big win", "Madagascar", "graduate based description",longstring, "May 17, 2019",u3));
        u3.getStories().add(new Story("fake title2 barnbarn", "Bolivia", "fakeish description","The enemy of my enemy is the enemy I kill last", "fake date",u3));
        u3.getStories().add(new Story("fake title3 barnbarn", "Bolivia", "fakeish description","Beam me up", "fake date","fakeurl.com",u3));
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("Bob", "password","John","Doe","john@doe.com","owner","USA", users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("Jane", "password","John","Doe","john@doe.com","owner","USA", users);
        userService.save(u5);

    }
    String longstring = "Mina Rakoto dances with her family and friends to celebrate graduating from high school at the top of her class. She hugs her family for their sacrifice and support. Mina grew up eight siblings in their family resort in Antananarivo. Although her parents were busy managing the hotel, Mina had attentive nannies and governesses taught her the value of working smart and thinking like an innovator. \n" +
            "\n" +
            "Regardless of local conflicts and family disputes, Mina studied hard to attend college to study business management to become a successful hotelier. Mina applied to several universities around the country and in other countries including the United States, Canada, Germany, and Australia. \n" +
            "\n" +
            "Mina is unaware that her family has thrown her a surprise party at the resort and plan on sharing with her the good news that she got accepted to a university in Germany. As the sun sets over the resort, the party begins as the Rakoto family enter the outside pavilion in style. The guests are ushered in as dinner is begun to be served. Families and guests congratulate Mina on her achievements and wish her good luck. \n" +
            "\n" +
            "As the festivities continue, Mina takes a stroll along the beach to reflect on her past and dream of her future. Her parents catch up with her and hand her a big glossy envelope. Mina sees the name of her dream university on the envelope and quickly opens the letter. She closes her eyes and takes a deep breath, trying to calm her racing heart. She opens her eyes and sees the word “congratulations” written in big bold letters. She jumps up and down and hugs her parents as yells loudly that she is accepted. Fireworks begin to paint the skies and families and friends gather to celebrate the graduate on her future big wins. \n";
}