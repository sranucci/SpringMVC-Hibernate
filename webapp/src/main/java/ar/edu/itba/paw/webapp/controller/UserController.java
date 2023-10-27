package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.dtos.BaseShowRecipeDataDto;
import ar.edu.itba.paw.dtos.RecipesAndSize;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.servicesInterface.UserService;
import ar.edu.itba.paw.servicesInterface.exceptions.InvalidTokenException;
import ar.edu.itba.paw.webapp.exceptions.InvalidArgumentException;
import ar.edu.itba.paw.servicesInterface.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.forms.EditProfileForm;
import ar.edu.itba.paw.webapp.forms.ForgotPasswordForm;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import ar.edu.itba.paw.webapp.forms.ResetPasswordForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.edu.itba.paw.webapp.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UserController {
    private final int PAGESIZE = 9;

    private UserService userService;
    private RecipeService recipeService;
    private AuthenticationManager authenticationManager;
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private BaseUserTemplateController baseUserTemplateController;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerForm(@ModelAttribute("registerForm") final RegisterForm registerForm) {
        return new ModelAndView("/register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final RegisterForm registerForm, final BindingResult errors) {
        if (errors.hasErrors())
            return registerForm(registerForm);
        
        try{
            User user = userService.createUser(registerForm.getEmail(), registerForm.getPassword(), registerForm.getFirstName(), registerForm.getLastName());
            LOGGER.info("Registered user with mail {} under id {}", user.getEmail(), user.getId());
            return new ModelAndView("redirect:/verifyAccountSend");
        }catch (UserAlreadyExistsException e){
            ModelAndView mav = new ModelAndView("/register");
            mav.addObject("error", true);
            return mav;
        }

    }

    @RequestMapping(value = "/verifyAccountSend", method = RequestMethod.GET)
    public ModelAndView verifyAccount() {
        return new ModelAndView("/verifyAccountSend");
    }

    @RequestMapping(value = "/verifyAccount/{token}", method = RequestMethod.GET)
    public ModelAndView verifyAccount(@PathVariable("token") final String token) {

        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(token, null);
            auth = authenticationProvider.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            LOGGER.error("token provided does not exist: {}.reason\n{}",token,e.getMessage());
            return new ModelAndView("redirect:/invalidVerifyToken");
        }
    }


    @RequestMapping(value = "/accountVerified", method = RequestMethod.GET)
    public ModelAndView accountVerified() {
        return new ModelAndView("/accountVerified");
    }

    @RequestMapping(value = "/invalidVerifyToken", method = RequestMethod.GET)
    public ModelAndView invalidVerifyToken() {
        return new ModelAndView("/invalidVerifyToken");
    }

    @RequestMapping(value = "/invalidResetPasswordToken", method = RequestMethod.GET)
    public ModelAndView invalidResetPasswordToken() {
        return new ModelAndView("/invalidResetPasswordToken");
    }


    private void loginUser(String email, String unHashedPassword) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, unHashedPassword);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/loginNotVerified", method = RequestMethod.GET)
    public ModelAndView loginNotVerified() {
        return new ModelAndView("/loginNotVerified");
    }

    @RequestMapping(value = "/resendVerification", method = RequestMethod.GET)
    public ModelAndView resendVerificationView() {
        return new ModelAndView("/resendVerification");
    }

    @RequestMapping(value = "/resendVerification", method = RequestMethod.POST)
    public ModelAndView resendVerification(@ModelAttribute("verifyAccount") final VerifyAccountForm verifyAccountForm, final BindingResult errors) {
        if (errors.hasErrors()) {
            return resendVerificationView();
        }


        if ( !userService.resendVerificationToken(verifyAccountForm.getEmail()) ) {
            final ModelAndView mav = new ModelAndView("/resendVerification");
            mav.addObject("error", "resendVerification.error");
            return mav;
        }

        return new ModelAndView("redirect:/verifyAccountSend");
    }


    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotPassword() {
        return new ModelAndView("/forgotPassword");
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ModelAndView forgotPassword(@Valid @ModelAttribute("forgotPasswordForm") final ForgotPasswordForm forgotPasswordForm, final BindingResult errors) {
        if (errors.hasErrors())
            return forgotPassword();

        final Optional<User> user = userService.findByEmail(forgotPasswordForm.getEmail());
        if (!user.isPresent()) {
            errors.rejectValue("email", "forgotPassword.error.invalidEmail");
            return forgotPassword();
        }

        userService.generateResetPasswordToken(user.get());
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/profile")
    public ModelAndView profile( @RequestParam(value = "page") Optional<Integer> page,
                                 @RequestParam("followersToBring") Optional<Integer> followersToBring,
                                 @RequestParam("followingToBring") Optional<Integer> followingToBring,
                                 @RequestParam(required = false, defaultValue = "false") final boolean openFollowersModal,
                                 @RequestParam(required = false, defaultValue = "false") final boolean openFollowingModal
                                 ) {
        pageValidation(page);
        usersToBringValidation(followersToBring);
        usersToBringValidation(followingToBring);
        Long currentId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotFoundException::new);
        User user = userService.findById(currentId).orElseThrow(UserNotFoundException::new);
        RecipesAndSize rl = recipeService.getRecipesForProfile(currentId, page, Optional.of(PAGESIZE));
        BaseShowRecipeDataDto dto = new BaseShowRecipeDataDto(
                "My Profile",
                "MyProfile",
                rl.getRecipeList(),
                page.orElse(1),
                PAGESIZE,
                rl.getTotalRecipes()
        );
        final ModelAndView mav = new ModelAndView("/profileWithRecipes");
        mav.addObject("user", user);
        mav.addObject("dto", dto);
        mav.addObject("followers", userService.getFollowers(currentId, currentId, followersToBring));
        mav.addObject("following", userService.getFollowing(currentId, currentId, followingToBring));
        mav.addObject("openFollowersModal", openFollowersModal);
        mav.addObject("openFollowingModal", openFollowingModal);
        return mav;
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ModelAndView profile(@PathVariable("id") final long profileId, @RequestParam(value = "page") Optional<Integer> page,
                                @RequestParam("followersToBring") Optional<Integer> followersToBring,
                                @RequestParam("followingToBring") Optional<Integer> followingToBring,
                                @RequestParam(required = false, defaultValue = "false") final boolean openFollowersModal,
                                @RequestParam(required = false, defaultValue = "false") final boolean openFollowingModal
                                ) {
        pageValidation(page);
        usersToBringValidation(followersToBring);
        usersToBringValidation(followingToBring);
        final ModelAndView mav = new ModelAndView("/profileWithRecipes");
        Long currentId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotFoundException::new);
        User user = userService.findById(profileId).orElseThrow(UserNotFoundException::new);
        RecipesAndSize rl = recipeService.getRecipesForProfile(user.getId(), page, Optional.of(PAGESIZE));
        BaseShowRecipeDataDto dto = new BaseShowRecipeDataDto(
                "Profile",
                "Profile",
                rl.getRecipeList(),
                page.orElse(1),
                PAGESIZE,
                rl.getTotalRecipes()
        );
        mav.addObject("dto", dto);
        mav.addObject("user", user);
        mav.addObject("isFollowed", userService.getFollows(currentId,profileId));
        mav.addObject("followers", userService.getFollowers(currentId, profileId, followersToBring));
        mav.addObject("following", userService.getFollowing(currentId, profileId, followingToBring));
        mav.addObject("openFollowersModal", openFollowersModal);
        mav.addObject("openFollowingModal", openFollowingModal);
        return mav;
    }

    private void pageValidation(Optional<Integer> page) {
        if (page.isPresent() && page.get() < 1)
            throw new InvalidArgumentException();
    }
    private void usersToBringValidation(Optional<Integer> usersToBring) {
        if (usersToBring.isPresent() && usersToBring.get() < 1)
            throw new InvalidArgumentException();
    }


    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView editProfile(@ModelAttribute("editProfileForm") final EditProfileForm editProfileForm) {
        final ModelAndView mav = new ModelAndView("/editProfile");
        User user = userService.findById(baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new)).orElseThrow(UserNotFoundException::new);
        editProfileForm.setFirstName(user.getName());
        editProfileForm.setLastName(user.getLastname());
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView editProfile(@Valid @ModelAttribute("editProfileForm") final EditProfileForm editProfileForm, final BindingResult errors) throws IOException {

        if (errors.hasErrors())
            return editProfile(editProfileForm);
        long userId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.updateProfile(userId, editProfileForm.getFirstName(), editProfileForm.getLastName(), editProfileForm.getUserPhoto().getBytes(), editProfileForm.isDeleteProfilePhoto());
        LOGGER.info("User with id {} updated its profile",userId);
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.GET)
    public ModelAndView resetPassword(@ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm, @PathVariable("token") final String token) {
        if (userService.validateToken(token)) {
            return new ModelAndView("/resetPassword");
        }
        return new ModelAndView("redirect:/invalidResetPasswordToken");
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(@Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm, final BindingResult errors, @ModelAttribute("token") final String token) {
        if (errors.hasErrors())
            return resetPassword(resetPasswordForm, resetPasswordForm.getToken());



        boolean success = true;
        try {
            User user = userService.resetPassword(resetPasswordForm.getToken(), resetPasswordForm.getPassword());
            loginUser(user.getEmail(), resetPasswordForm.getPassword());
        } catch (InvalidTokenException invalidTokenException) {
            success = false;
        }


        final ModelAndView mav = new ModelAndView("/resetPasswordRequest");
        mav.addObject("success", success);
        return mav;
    }


    @RequestMapping(value = "/recipeDetail/{recipeId:\\d+}/follow/{id:\\d+}", method = RequestMethod.POST)
    public ModelAndView followUserFromRecipe(@PathVariable final long recipeId, @PathVariable final long id){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.setFollowing(currentUserId, id);
        return new ModelAndView("redirect:/recipeDetail/" + recipeId);
    }

    @RequestMapping(value = "/recipeDetail/{recipeId:\\d+}/unfollow/{id:\\d+}", method = RequestMethod.POST)
    public ModelAndView unfollowUserFromRecipe(@PathVariable final long recipeId, @PathVariable final long id){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.removeFollowing(currentUserId, id);
        return new ModelAndView("redirect:/recipeDetail/" + recipeId);
    }

    @RequestMapping(value = "/profile/follow/{idToFollow:\\d+}", method = RequestMethod.POST)
    public ModelAndView followUser(@PathVariable final long idToFollow, @RequestParam final long page, RedirectAttributes redirectAttrs){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.setFollowing(currentUserId, idToFollow);
        String redirectUrl = "/profile/" + idToFollow;
        if (page > 1) {
            redirectAttrs.addAttribute("page", page);
        }
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @RequestMapping(value = "/profile/unfollow/{idToFollow:\\d+}", method = RequestMethod.POST)
    public ModelAndView unfollowUser(@PathVariable final long idToFollow, @RequestParam final long page, RedirectAttributes redirectAttrs){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.removeFollowing(currentUserId, idToFollow);
        String redirectUrl = "/profile/" + idToFollow;
        if (page > 1) {
            redirectAttrs.addAttribute("page", page);
        }
        return new ModelAndView("redirect:" + redirectUrl);
    }



    @RequestMapping(value = "/profile/{id:\\d+}/follow/{idToFollow:\\d+}", method = RequestMethod.POST)
    public ModelAndView followUserFromProfileList(@PathVariable final long id, @PathVariable final long idToFollow,
                                                  @RequestParam final long page,
                                                  @RequestParam(required = false, defaultValue = "false") final boolean openFollowersModal,
                                                  @RequestParam(required = false, defaultValue = "false") final boolean openFollowingModal,
                                                  RedirectAttributes redirectAttrs
                                                  ){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.setFollowing(currentUserId, idToFollow);
        return redirectToProfile(id, page, openFollowersModal, openFollowingModal, redirectAttrs, currentUserId);
    }


    @RequestMapping(value = "/profile/{id:\\d+}/unfollow/{idToFollow:\\d+}", method = RequestMethod.POST)
    public ModelAndView unfollowUserFromProfileList(@PathVariable final long id, @PathVariable final long idToFollow,
                                                    @RequestParam final long page,
                                                    @RequestParam(required = false, defaultValue = "false") final boolean openFollowersModal,
                                                    @RequestParam(required = false, defaultValue = "false") final boolean openFollowingModal,
                                                    RedirectAttributes redirectAttrs){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.removeFollowing(currentUserId, idToFollow);
        return redirectToProfile(id, page, openFollowersModal, openFollowingModal, redirectAttrs, currentUserId);
    }

    private ModelAndView redirectToProfile(long id, long page, boolean openFollowersModal,  boolean openFollowingModal, RedirectAttributes redirectAttrs, long currentUserId) {
        String redirectUrl = (currentUserId == id) ? "/profile" : "/profile/" + id;
        if (page > 1) {
            redirectAttrs.addAttribute("page", page);
        }
        redirectAttrs.addAttribute("openFollowersModal", openFollowersModal);
        redirectAttrs.addAttribute("openFollowingModal", openFollowingModal);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @RequestMapping(value = "/feed/follow/{idToFollow:\\d+}", method = RequestMethod.POST)
    public ModelAndView followUserFromFeed(@PathVariable final long idToFollow){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.setFollowing(currentUserId, idToFollow);
        return new ModelAndView("redirect:" + "/following");
    }

    @RequestMapping(value = "/feed/unfollow/{idToFollow:\\d+}", method = RequestMethod.POST)
    public ModelAndView unfollowUserFromFeed(@PathVariable final long idToFollow){
        long currentUserId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        userService.removeFollowing(currentUserId, idToFollow);
        return new ModelAndView("redirect:" + "/following");
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRecipeService(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @Autowired
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider){
        this.authenticationProvider = authenticationProvider;
    }
}
