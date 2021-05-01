package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.UserGroup;
import com.nikhilsujith.sayitrightapi.model.GroupMember;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.repository.GroupRepository;
import com.nikhilsujith.sayitrightapi.repository.UserRepository;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupService {

    //    Repository
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    S3Service s3Service;


    //    Get all groups
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    //    Get group by creator Name
    public Optional<List<Group>> findGroupByCreatorId(String creatorId) {
        return groupRepository.getGroupByCreatorId(creatorId);
    }

    //  Get users by group id
    public List<GroupMember> findUsersByGroupId(String groupId) {
        Optional<Group> g = groupRepository.findById(new ObjectId(groupId));
        return g.get().users;
    }

    // Delete group
    public void deleteGroup(ObjectId id) {
        groupRepository.deleteById(id);
    }

//    Get group by creator Id
//    public Optional<List<Group>> findGroupByCreatorId(String creatorId){
//        return repository.getGroupByCreatorName(creatorId);
//    }


    /*--------------------------------POST--------------------------------*/
//    Insert new group
//    public void createNewGroup(Group group ){
//        repository.save(new Group(group.get_id(), group.getGroupName(), group.getGroupDesc(), group.getGroupImage(), group.getCreatorId(), group.getCreatorName()));
//    }

    public String createNewGroup(Group group) {
        Group g = groupRepository.save(group);
        return g.id;
    }

    public String updateGroup(Group group,String poolId) {
        try{
            Optional<Group> g = groupRepository.findById(new ObjectId(group.id));
            if(g.get().createrPoolId.equals(poolId)){
                g.get().id=group.id;
                g.get().groupDesc=group.groupDesc;
                g.get().groupImage=group.groupImage;
                g.get().groupName=group.groupName;
                groupRepository.save(g.get());


                for(int i=0;i<group.users.size();i++){
                    String user_id=group.users.get(i).id;
                    Optional<User> u=userRepository.findById(new ObjectId(user_id));

                    //update group details inside enrolled groups list inside user
                    for(int j=0;j<u.get().enrolledGroups.size();j++){
                        String grpId=u.get().enrolledGroups.get(j).id;
                        if(grpId.equals(g.get().id)){
                            u.get().enrolledGroups.get(j).groupDesc=group.groupDesc;
                            u.get().enrolledGroups.get(j).groupImage=group.groupImage;
                            u.get().enrolledGroups.get(j).groupName=group.groupName;
                            userRepository.save(u.get());
                        }
                    }

                    //update group details inside owner's groups list inside user
                    for(int j=0;j<u.get().myGroups.size();j++){
                        String grpId=u.get().myGroups.get(j).id;
                        if(grpId.equals(g.get().id)){
                            u.get().myGroups.get(j).groupDesc=group.groupDesc;
                            u.get().myGroups.get(j).groupImage=group.groupImage;
                            u.get().myGroups.get(j).groupName=group.groupName;
                            userRepository.save(u.get());
                        }
                    }

                    //userRepository.save(u.get());
                }
                return "success";
            }
            else{
                return "User don't have access to update the group";
            }
        }
        catch(Exception ex) {
            return ex.toString();
        }
    }

    public String enrollNewGroup(String group_id, String pool_id) {
        //return "group_id:"+group_id+", user_id:"+user_id;

        try {
            Optional<Group> g = groupRepository.findById(new ObjectId(group_id));
            ObjectId user_id = userService.getUserIdFromPoolId(pool_id);
            Optional<User> u = userRepository.findById(user_id);
            int flag1=0;
            int flag2=0;
            for (UserGroup value : u.get().enrolledGroups) {
                if(group_id.equals(value.id)){
                    flag1=1;
                }
            }
            for (GroupMember value : g.get().users) {
                if(group_id.equals(value.id)){
                    flag2=1;
                }
            }

            if(flag1!=1 && flag2!=1){
                UserGroup ug = new UserGroup();
                ug.id = g.get().id;
                ug.groupName = g.get().groupName;
                ug.groupImage = g.get().groupImage;
                ug.groupDesc = g.get().groupDesc;
                u.get().enrolledGroups.add(ug);


                GroupMember gm = new GroupMember();
                gm.id = u.get().id;
                gm.fullName = u.get().fullName;
                gm.poolId = u.get().poolId;
                gm.profileImage = u.get().profileImage;
                g.get().users.add(gm);

                userRepository.save(u.get());
                groupRepository.save(g.get());

                return "success";
            }
            else{
                return "User already a member of the group";
            }

        } catch (Exception ex) {
            return ex.toString();
        }

    }

    /*------------------------Image---------------------------*/
    public String uploadImage(String groupId, MultipartFile file,String poolId){
        String ImageResponse =  s3Service.uploadImage(groupId, file);
        updateGroupImageLink(groupId, ImageResponse);
        updateGroupImageLinkInUser(groupId,poolId,ImageResponse);
        return ImageResponse;
    }

//    Update group image link in group
    public void updateGroupImageLink(String groupId, String link){
        Optional<Group> group = groupRepository.findById(new ObjectId(groupId));
        Group updateGroup = group.orElse(null);
        assert updateGroup != null;
        updateGroup.setGroupImage(link);
        groupRepository.save(updateGroup);
    }

    //    Update group image link in group
    public void updateGroupImageLinkInUser(String groupId,String poolId, String link){
        Optional<User> user = userRepository.findById(new ObjectId(poolId));
        for(int i=0;i<user.get().myGroups.size();i++){
            if(groupId.equals(user.get().myGroups.get(i).id)){
                user.get().myGroups.get(i).groupImage=link;
            }
        }
        userRepository.save(user.get());
    }

    public String removeGroupMember(String creator_pool_id,String group_id,String pool_id){
        //return "group_id:"+group_id+", user_id:"+user_id;

        try{
            Optional<Group> g=groupRepository.findById(new ObjectId(group_id));
            ObjectId user_id=userService.getUserIdFromPoolId(pool_id);
            Optional<User> u=userRepository.findById(user_id);
            //String x="";
            int flag1=0;
            int flag2=0;
            for (GroupMember value : g.get().users) {
                //x=x+value.poolId+",";
                if(pool_id.equals(value.poolId)){
                    if(creator_pool_id.equals(g.get().createrPoolId)){
                        g.get().users.remove(value);
                        flag1=1;
                        break;
                    }
                    else{
                        return "Alert: user don't have access to delete the record!";
                    }
                }
            }
            for (UserGroup value : u.get().enrolledGroups) {
                //x=x+value.poolId+",";
                if(group_id.equals(value.id)){
                    u.get().enrolledGroups.remove(value);
                    flag2=1;
                    break;
                }
            }

            if(flag1==1 && flag2==1){
                groupRepository.save(g.get());
                userRepository.save(u.get());
                return "success";
            }
            else{
                return "Record not deleted!";
            }
        }
        catch(Exception ex) {
            return ex.toString();
        }
    }

    public String exitGroup(String group_id,String pool_id){
        //return "group_id:"+group_id+", user_id:"+user_id;

        try{
            Optional<Group> g=groupRepository.findById(new ObjectId(group_id));
            ObjectId user_id=userService.getUserIdFromPoolId(pool_id);
            Optional<User> u=userRepository.findById(user_id);
            //String x="";
            int flag1=0;
            int flag2=0;
            for (GroupMember value : g.get().users) {
                //x=x+value.poolId+",";
                if(pool_id.equals(value.poolId)){
                        g.get().users.remove(value);
                        flag1=1;
                        break;
                }
            }
            for (UserGroup value : u.get().enrolledGroups) {
                //x=x+value.poolId+",";
                if(group_id.equals(value.id)){
                    u.get().enrolledGroups.remove(value);
                    flag2=1;
                    break;
                }
            }

            if(flag1==1 && flag2==1){
                groupRepository.save(g.get());
                userRepository.save(u.get());
                return "success";
            }
            else{
                return "Record not deleted!";
            }
        }
        catch(Exception ex) {
            return ex.toString();
        }
    }

}
