package ru.ryatronth.sd.ticket.adapter;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.iamsync.api.IamUsersClientV1;
import ru.ryatronth.sd.iamsync.dto.IamUserDto;

@Component
@RequiredArgsConstructor
public class IamUsersClientAdapter {

  private final IamUsersClientV1 iamUsersClient;

  public IamUserDto getByIdOrThrow(UUID id) {
    var body = iamUsersClient.getUserById(id).getBody();
    if (body == null) {
      throw new NotFoundException("Пользователь не найден в IAM Sync: " + id);
    }
    return body;
  }

}
