package ca.com.rlsp.rlspfoodapi.core.modelmapper;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ItemOrderInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.AddressOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v2.model.input.CityInputDtoV2;
import ca.com.rlsp.rlspfoodapi.domain.model.Address;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /*
        Como o Model Mapper nao eh controlado pelo SpringBoot temos que fazer ess asconfiguracao para anexa-lo dentro do SpringBoot
     */
    @Bean
    public ModelMapper modelMapper(){

        var modelMapper = new ModelMapper();

        /*
         * Customizing moddelMapper to mapping a customized atttribute
         *
         *
		 *   modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
         * 	        .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
         *
         * return modelMapper;
         */

        /*
            Mapeia apenas o nome do Estado dentro do Enderaco
         */



        var addressToAddressOuptDto = modelMapper.createTypeMap(
				Address.class, AddressOutputDto.class);

        addressToAddressOuptDto.<String>addMapping(
				addressSrc -> addressSrc.getCity().getProvince().getName(),
				(addressOutputDest, value) -> addressOutputDest.getCity().setProvince(value));


        /*
            SKIP o ID do Order Items dentro de Order
         */
        modelMapper.createTypeMap(ItemOrderInputDto.class, OrderItem.class)
                .addMappings(mapper -> mapper.skip(OrderItem::setId));

        /*
            SKIP o ID do Order Items dentro de Order
         */
        modelMapper.createTypeMap(CityInputDtoV2.class, City.class)
                .addMappings(mapper -> mapper.skip(City::setId));

        //return new ModelMapper();
        return modelMapper;
    }
}
