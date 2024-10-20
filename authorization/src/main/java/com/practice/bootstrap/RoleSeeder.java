package com.practice.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.practice.entities.Role;
import com.practice.entities.RoleEnum;
import com.practice.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.sql.Timestamp;

@Component
@Slf4j
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleEnums = new RoleEnum[] { RoleEnum.USER, RoleEnum.ADMIN,
                RoleEnum.SUPER_ADMIN };
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role");

        Arrays.stream(roleEnums).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                Timestamp current = new Timestamp(System.currentTimeMillis());

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                roleToCreate.setCreatedAt(current);
                roleToCreate.setUpdatedAt(current);

                roleRepository.save(roleToCreate);
            });

        });
    }
}
