package id.jostudios.penielcommunityx.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.jostudios.penielcommunityx.data.cache.model.UserCacheModel
import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.enums.RolesEnum
import id.jostudios.penielcommunityx.domain.model.UserModel

object CacheConverter {
    private val gson: Gson = Gson();

    public fun fromRawGroup(raw: String): List<GroupsEnum> {
        val model = gson.fromJson<List<GroupsEnum>>(raw, object : TypeToken<List<GroupsEnum>>(){}.type);
        return model;
    }

    public fun toRawGroup(model: List<GroupsEnum>): String {
        val raw = gson.toJson(model);
        return raw;
    }

    public fun fromRawRole(raw: String): RolesEnum {
        return RolesEnum.valueOf(raw);
    }

    public fun toRawRole(model: RolesEnum): String {
        return model.name;
    }

    public fun fromUserCache(cached: UserCacheModel): UserModel {
        val model: UserModel = UserModel(
            id = cached.id,
            displayName = cached.displayName,
            email = cached.email,
            phoneNumber = cached.phoneNumber,
            birthDate = cached.birthDate,
            profilePicture = cached.profilePicture,
            groups = fromRawGroup(cached.groups),
            permissions = cached.permissions,
            name = cached.name,
            role = fromRawRole(cached.role),
            isDisplayEmail = cached.isDisplayEmail,
            isDisplayPhone = cached.isDisplayPhone,
            realName = cached.realName
        );

        return model;
    }

    public fun toUserCache(model: UserModel): UserCacheModel {
        val cached: UserCacheModel = UserCacheModel(
            id = model.id,
            displayName = model.displayName,
            email = model.email,
            phoneNumber = model.phoneNumber,
            birthDate = model.birthDate,
            profilePicture = model.profilePicture,
            groups = toRawGroup(model.groups),
            permissions = model.permissions,
            name = model.name,
            role = toRawRole(model.role),
            isDisplayEmail = model.isDisplayEmail,
            isDisplayPhone = model.isDisplayPhone,
            realName = model.realName
        );

        return cached;
    }
}