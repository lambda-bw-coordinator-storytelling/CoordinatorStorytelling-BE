package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.Story;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.UserRoles;
import com.lambdaschool.starthere.repository.RoleRepository;
import com.lambdaschool.starthere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userrepos.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id)
    {
        if (userrepos.findById(id).isPresent())
        {
            userrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setCountry(user.getCountry());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setTitle(user.getTitle());



        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);

        for (Story q : user.getStories())
//            public Story(String title, String country, String description, String content, String date, User user) {

        {
            newUser.getStories().add(new Story(q.getTitle(),q.getCountry(),q.getDescription(),q.getContent(),q.getDate(), newUser));
        }

        return userrepos.save(newUser);
    }


    @Transactional
    @Override
    public User update(User user, long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());

        if (currentUser != null)
        {
            if (id == currentUser.getUserid())
            {
                if (user.getUsername() != null)
                {
                    currentUser.setUsername(user.getUsername());
                }

                if (user.getPassword() != null)
                {
                    currentUser.setPasswordNoEncrypt(user.getPassword());
                }
                if (user.getFirstname() !=null){
                    currentUser.setFirstname(user.getFirstname());
                }
                if (user.getLastname() !=null){
                    currentUser.setLastname(user.getLastname());
                }
                if (user.getEmail() !=null){
                    currentUser.setEmail(user.getEmail());
                }
                if (user.getTitle() !=null){
                    currentUser.setTitle(user.getTitle());
                }
                if (user.getCountry() !=null){
                    currentUser.setCountry(user.getCountry());
                }

                if (user.getUserRoles().size() > 0)
                {
                    // with so many relationships happening, I decided to go
                    // with old school queries
                    // delete the old ones
                    rolerepos.deleteUserRolesByUserId(currentUser.getUserid());

                    // add the new ones
                    for (UserRoles ur : user.getUserRoles())
                    {
                        rolerepos.insertUserRoles(id, ur.getRole().getRoleid());
                    }
                }

                if (user.getStories().size() > 0)
                {
                    for (Story q : user.getStories())
                    {
                        currentUser.getStories().add(new Story(q.getTitle(),q.getCountry(),q.getDescription(),q.getContent(),q.getDate(), currentUser));
                    }
                }

                return userrepos.save(currentUser);
            } else
            {
                throw new ResourceNotFoundException(id + " Not current user");
            }
        } else
        {
            throw new ResourceNotFoundException(authentication.getName());
        }

    }
}
