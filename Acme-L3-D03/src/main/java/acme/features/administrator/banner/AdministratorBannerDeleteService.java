
package acme.features.administrator.banner;

import org.springframework.stereotype.Service;

import acme.entities.banners.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

}
