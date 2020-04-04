package com.atharva.auth.user.service;

import com.atharva.auth.user.client.AuthFeignClient;
import com.atharva.auth.user.constants.ErrorCodes;
import com.atharva.auth.user.dao.UserDataRepository;
import com.atharva.auth.user.model.Response;
import com.atharva.auth.user.model.UserId;
import com.atharva.auth.user.model.UserModel;
import com.atharva.auth.user.utils.encrypt.EncryptUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    private AuthFeignClient authClient;

    @Autowired
    private UserDataRepository dao;

    public ErrorCodes register(String project_auth, String user_auth, String userData) {
        Response response = authClient.register(project_auth, user_auth);

        if (response.getCode() == ErrorCodes.SUCCESS) {
            final String[] projectSplit = project_auth.split(":");
            final String projectId = new String(Base64.getDecoder().decode(projectSplit[0]));

            final String[] userSplit = user_auth.split(":");
            final String id = new String(Base64.getDecoder().decode(userSplit[0]));

            UserModel data
                    = new UserModel(
                            id,
                            projectId,
                            EncryptUtils.doUserEF(projectSplit[1], response.getData(), userData)
                    );

            dao.save(data);
            return ErrorCodes.SUCCESS;

        } else {
            return response.getCode();
        }
    }

    public Response getData(String project_auth, String user_auth) {
        Response response = authClient.login(project_auth, user_auth);

        if (response.getCode() == ErrorCodes.SUCCESS) {
            final String[] projectSplit = project_auth.split(":");
            final String projectId = new String(Base64.getDecoder().decode(projectSplit[0]));

            final String[] userSplit = user_auth.split(":");
            final String id = new String(Base64.getDecoder().decode(userSplit[0]));

            UserId userId = new UserId(id, projectId);

            if (dao.existsById(userId)) {
                UserModel data = dao.getOne(userId);
                String decryptedData = EncryptUtils.undoUserEF(projectSplit[1], response.getData(), data.getData());
                return new Response(ErrorCodes.SUCCESS, decryptedData);

            } else  {
                return new Response(ErrorCodes.UNKNOWN, null);
            }

        } else {
            return new Response(response.getCode(), null);
        }
    }

    public ErrorCodes updateData(String project_auth, String user_auth, String userData) {
        Response response = authClient.login(project_auth, user_auth);

        if (response.getCode() == ErrorCodes.SUCCESS) {
            final String[] projectSplit = project_auth.split(":");
            final String projectId = new String(Base64.getDecoder().decode(projectSplit[0]));

            final String[] userSplit = user_auth.split(":");
            final String id = new String(Base64.getDecoder().decode(userSplit[0]));

            UserId userId = new UserId(id, projectId);

            if (dao.existsById(userId)) {
                UserModel data = dao.getOne(userId);
                data.setData(EncryptUtils.doUserEF(projectSplit[1], response.getData(), userData));
                dao.save(data);
                return ErrorCodes.SUCCESS;

            } else  {
                return ErrorCodes.UNKNOWN;
            }

        } else {
            return response.getCode();
        }
    }

    public ErrorCodes changePassword(String project_auth, String user_old_auth, String user_new_auth) throws JsonProcessingException {
        Response response = authClient.change(user_new_auth, user_old_auth, project_auth);

        if (response.getCode() == ErrorCodes.SUCCESS) {
            final String[] projectSplit = project_auth.split(":");
            final String projectId = new String(Base64.getDecoder().decode(projectSplit[0]));

            final String[] userSplit = user_new_auth.split(":");
            final String id = new String(Base64.getDecoder().decode(userSplit[0]));

            UserId userId = new UserId(id, projectId);

            if (dao.existsById(userId)) {
                UserModel data = dao.getOne(userId);
                Map<String, String> resMap = new ObjectMapper().readValue(response.getData(), new TypeReference<>() {});
                String decryptedData = EncryptUtils.undoUserEF(projectSplit[1], resMap.get("oldKey"), data.getData());
                data.setData(EncryptUtils.doUserEF(projectSplit[1], resMap.get("newKey"), decryptedData));
                dao.save(data);
                return ErrorCodes.SUCCESS;

            } else  {
                return ErrorCodes.UNKNOWN;
            }

        } else {
            return response.getCode();
        }
    }
}
