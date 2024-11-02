package id.jostudios.penielcommunityx.presentation.activities.members_activity

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.CacheConverter
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.data.cache.model.CacheControlModel
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.domain.repository.CacheControlRepository
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.domain.repository.StorageRepository
import id.jostudios.penielcommunityx.domain.repository.UserCacheRepository
import id.jostudios.penielcommunityx.domain.use_case.get_members.GetMembersUseCase
import id.jostudios.penielcommunityx.domain.use_case.get_profile_picture.GetProfilePictureUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    private val storageRepo: StorageRepository,
    private val getMembersUseCase: GetMembersUseCase,

    private val cacheControlRepo: CacheControlRepository,
    private val usersCacheRepo: UserCacheRepository,
    private val dbRepo: DatabaseRepository
): ViewModel() {

    private val _state: MutableState<MemberState> = mutableStateOf(MemberState());
    val state: State<MemberState> = _state;

    init {
        fetchMembers();
    }

    fun closeError() {
        _state.value = _state.value.copy(
            error = null
        );
    }

    fun setLoading(value: Boolean) {
        _state.value = _state.value.copy(
            isLoading = value
        );
    }

    private fun fetchMembers() {
        viewModelScope.launch {
            setLoading(true);

            // Check last update \\
            val serverLastUpdate = dbRepo.getLastUsersUpdate();
            val cacheLastUpdate = cacheControlRepo.getValue("lastUsersUpdate")?.value?.toLong()!!;

            if (serverLastUpdate != cacheLastUpdate) {
                // Cache need new data from server
                Log.d("MembersViewModel", "Downloading new data");
                val tempUsers = dbRepo.getUsers();
                tempUsers.forEach { user ->
                    val cached = CacheConverter.toUserCache(user);
                    usersCacheRepo.insertUser(cached);
                }

                Log.d("MembersViewModel", "Updating cache");

                cacheControlRepo.update(
                    CacheControlModel(1, "lastUsersUpdate", serverLastUpdate.toString())
                );

                Log.d("MembersViewModel", "Cache updated!");
            }
            // Check last update \\

            val cached = usersCacheRepo.getCachedUsers();
            val tempData: MutableList<UserModel> = mutableListOf();

            cached.forEach { user ->
                tempData.add(CacheConverter.fromUserCache(user));
            }

            _state.value = _state.value.copy(
                members = tempData
            );

            Log.d("MembersViewModel", "Fetching members from cache!");

            setLoading(false);
        }
//        getMembersUseCase().onEach { res ->
//            when(res) {
//                is Resource.Success -> {
//                    _state.value = _state.value.copy(
//                        members = res.data,
//                        isLoading = false
//                    )
//                }
//                is Resource.Error -> {
//                    _state.value = _state.value.copy(
//                        error = res.message,
//                        isLoading = false
//                    )
//                }
//                is Resource.Loading -> {
//                    _state.value = _state.value.copy(
//                        isLoading = true
//                    )
//                }
//            }
//        }.launchIn(viewModelScope);
    }

    public suspend fun fetchUserProfile(name: String): Uri? {
        try {
            return storageRepo.getProfilePicture(name);
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = e.localizedMessage,
                isLoading = false
            )
            return null;
        }
    }
}