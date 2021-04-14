// TODO: DISCARD
//
//package edu.byu.cs.tweeter.server.dao;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import edu.byu.cs.tweeter.model.domain.User;
//import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
//import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
//
//public class FollowerDAO {
//    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
//    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
//
//    private User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
//    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
//    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
//    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
//    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
//    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
//    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
//    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
//    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
//    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
//    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
//    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
//    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
//    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
//    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
//    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
//    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
//    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
//    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
//    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);
//
//    public FollowerResponse getFollowers(FollowerRequest request) {
//
//        List<User> allFollowers = getDummyFollowers();
//
//        int requestLimit = request.getLimit();
//        if (requestLimit < 8) { // Ensure the request limit is at least 8.
//            requestLimit = 8;
//        }
//
//        List<User> responseFollowers = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = true;
//
//        if(requestLimit > 0) {
//            if (allFollowers != null) {
//                int followersIndex = getFolloweesStartingIndex(request.getLastFollower(), allFollowers);
//
//                if (request.getLastFollower() == null){
//                    followersIndex = 0;
//                }
//
//                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < requestLimit; followersIndex++, limitCounter++) {
//                    responseFollowers.add(allFollowers.get(followersIndex));
//                }
//
//                hasMorePages = followersIndex < allFollowers.size();
//            }
//        }
//
//        return new FollowerResponse(responseFollowers, hasMorePages);
////        return new FollowerResponse(allFollowers, hasMorePages);
//    }
//
//    /**
//     * Returns the list of dummy follower data. This is written as a separate method to allow
//     * mocking of the followers.
//     *
//     * @return the generator.
//     */
//    List<User> getDummyFollowers() {
//        user1.setFollowingCount(10);
//        user1.setFollowersCount(17);
//        user2.setFollowingCount(10);
//        user2.setFollowersCount(17);
//        user3.setFollowingCount(10);
//        user3.setFollowersCount(17);
//        user4.setFollowingCount(10);
//        user4.setFollowersCount(17);
//        user5.setFollowingCount(10);
//        user5.setFollowersCount(17);
//        user6.setFollowingCount(10);
//        user6.setFollowersCount(17);
//        user7.setFollowingCount(10);
//        user7.setFollowersCount(17);
//        user8.setFollowingCount(10);
//        user8.setFollowersCount(17);
//        user9.setFollowingCount(10);
//        user9.setFollowersCount(17);
//        user10.setFollowingCount(10);
//        user10.setFollowersCount(17);
//        user11.setFollowingCount(10);
//        user11.setFollowersCount(17);
//        user12.setFollowingCount(10);
//        user12.setFollowersCount(17);
//        user13.setFollowingCount(10);
//        user13.setFollowersCount(17);
//        user14.setFollowingCount(10);
//        user14.setFollowersCount(17);
//        user15.setFollowingCount(10);
//        user15.setFollowersCount(17);
//        user16.setFollowingCount(10);
//        user16.setFollowersCount(17);
//        user17.setFollowingCount(10);
//        user17.setFollowersCount(17);
//        user18.setFollowingCount(10);
//        user18.setFollowersCount(17);
//        user19.setFollowingCount(10);
//        user19.setFollowersCount(17);
//        user20.setFollowingCount(10);
//        user20.setFollowersCount(17);
//
//        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
//                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
//                user19, user20);
//    }
//
//
//    /**
//     * Determines the index for the first follower in the specified 'allFollowers' list that should
//     * be returned in the current request. This will be the index of the next follower after the
//     * specified 'lastFollower'.
//     *
//     * @param lastFollower the last follower that was returned in the previous request or null if
//     *                     there was no previous request.
//     * @param allFollowers the generated list of followers from which we are returning paged results.
//     * @return the index of the first follower to be returned.
//     */
//    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {
//
//        int followeesIndex = 0;
//
//        if(lastFollowee != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowees.size(); i++) {
//                if(lastFollowee.equals(allFollowees.get(i))) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followeesIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return followeesIndex;
//    }
//}
