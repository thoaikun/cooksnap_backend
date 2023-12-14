package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.domains.dto.ErrorResponseDto;
import com.cooksnap.backend.domains.dto.SuccessResponse;
import com.cooksnap.backend.domains.dto.requests.AddDishRequest;
import com.cooksnap.backend.domains.dto.responses.DishHasInFavoriteListResponse;
import com.cooksnap.backend.domains.entity.*;
import com.cooksnap.backend.repositories.DishRepository;
import com.cooksnap.backend.repositories.FavoriteDishRepository;
import com.cooksnap.backend.repositories.FavoriteListRepository;
import com.cooksnap.backend.services.servicesInterface.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final FavoriteListRepository favoriteListRepository;
    private final FavoriteDishRepository favoriteDishRepository;
    public ResponseEntity<?> getFavoriteDish(int listId){
        try {
            List<Dish> list_dish = dishRepository.getFavoriteDish(listId);
            return ResponseEntity.ok().body(list_dish);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }


    public ResponseEntity<?> addFavoriteListByUserId(String name, Principal connectedUser){
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            FavoriteList newFavoriteList = new FavoriteList();
            newFavoriteList.setListName(name);
            newFavoriteList.setUserId(user.getUserId());
            favoriteListRepository.save(newFavoriteList);
            return ResponseEntity.ok().body(new SuccessResponse("add success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }

    public ResponseEntity<?> getFavoriteListByUserId(Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        try{
            List<FavoriteList> favoriteLists = favoriteListRepository.findByUserId(user.getUserId());
            return ResponseEntity.ok().body(favoriteLists);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }

    public ResponseEntity<?> addDishToFavoriteList(AddDishRequest request, Principal connectedUser){
        try {
            Optional<Dish> dishOptional =  dishRepository.findByAbout(request.getAboutDish());
            Dish dish;

            if (dishOptional.isEmpty()){
                Dish newDish = new Dish();
                newDish.setDish_id(request.getDishId());
                newDish.setAbout(request.getAboutDish());
                dishRepository.saveAndFlush(newDish);
                dishOptional = dishRepository.findByAbout(request.getAboutDish());
                dish = dishOptional.orElse(newDish);
            }
            else
                dish = dishOptional.get();



            FavoriteDish favoriteDish = new FavoriteDish();
            favoriteDish.setFavoriteListId(request.getListId());
            favoriteDish.setDishId(dish.getDish_id());

            favoriteDishRepository.save(favoriteDish);


            return ResponseEntity.ok().body(new SuccessResponse("add success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }

    public ResponseEntity<?> deleteDishToFavoriteList(Integer listId, String dishId, Principal connectedUser){
        try {
            favoriteDishRepository.deleteFavoriteDishByDishIdAndFavoriteListId(dishId, listId);
            return ResponseEntity.ok().body(new SuccessResponse("delete success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }


    public ResponseEntity<?> deleteFavorite(int listId){
        try{
            favoriteDishRepository.deleteByFavoriteListId(listId);
            favoriteListRepository.deleteById(listId);
            return ResponseEntity.ok().body(new SuccessResponse("delete success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }

    @Override
    public ResponseEntity<?> isInYourFavorite(String dishId, Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            List<Object> objects = favoriteDishRepository.getFavoriteDishWithUserId(dishId, user.getUserId());
            if (objects.isEmpty()) {
                List<Object> favoriteListIds = new ArrayList<>();
                return ResponseEntity.ok().body(new DishHasInFavoriteListResponse(false, favoriteListIds));
            }
            else {
                List<Object> favoriteListIds = new ArrayList<>();
                for (Integer i=0; i < objects.size(); i++) {
                    favoriteListIds.add(objects.get(i));
                }
                return ResponseEntity.ok().body(new DishHasInFavoriteListResponse(true, favoriteListIds));
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }
}
