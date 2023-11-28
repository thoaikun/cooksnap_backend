package com.unidy.backend.mapper;

import com.unidy.backend.domains.dto.UserDto;
import com.unidy.backend.domains.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-25T02:41:10+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public void updateUserInformation(UserDto newData, User data) {
        if ( newData == null ) {
            return;
        }

        if ( newData.getUserId() != null ) {
            data.setUserId( newData.getUserId() );
        }
        if ( newData.getFullName() != null ) {
            data.setFullName( newData.getFullName() );
        }
        if ( newData.getAddress() != null ) {
            data.setAddress( newData.getAddress() );
        }
        if ( newData.getDayOfBirth() != null ) {
            data.setDayOfBirth( newData.getDayOfBirth() );
        }
        if ( newData.getSex() != null ) {
            data.setSex( newData.getSex() );
        }
        if ( newData.getPhone() != null ) {
            data.setPhone( newData.getPhone() );
        }
        if ( newData.getEmail() != null ) {
            data.setEmail( newData.getEmail() );
        }
        if ( newData.getJob() != null ) {
            data.setJob( newData.getJob() );
        }
        if ( newData.getWorkLocation() != null ) {
            data.setWorkLocation( newData.getWorkLocation() );
        }
        if ( newData.getPassword() != null ) {
            data.setPassword( newData.getPassword() );
        }
        if ( newData.getRole() != null ) {
            data.setRole( newData.getRole() );
        }
    }
}
