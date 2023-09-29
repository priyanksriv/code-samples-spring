package com.prizy.product.service.impl;

import com.prizy.common.exception.RecordNotFoundException;
import com.prizy.product.domain.entity.PricingDetails;
import com.prizy.product.domain.entity.Product;
import com.prizy.product.domain.repository.PricingDetailsRepository;
import com.prizy.product.domain.repository.ProductRepository;
import com.prizy.product.mapper.ProductToProductVOMapper;
import com.prizy.product.mapper.ProductVOToProductMapper;
import com.prizy.product.service.ProductService;
import com.prizy.product.vo.PricingDetailsVO;
import com.prizy.product.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {

    private Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Override
    public List<ProductVO> findAllProducts() {
        Iterable<Product> entities = productRepository.findAll();
        return productToProductVOMapper.mapList(entities);
    }

    @Transactional
    @Override
    public ProductVO createProduct(ProductVO productVO) {
        productVO.setId(UUID.randomUUID().toString());
        Product product = productRepository.save(productVOToProductMapper.map(productVO));

        LOG.info("Saved Product: {}", product);
        return productToProductVOMapper.map(product);
    }

    @Override
    public ProductVO findProduct(String productId) {
        Product product = productRepository.findOne(productId);
        if (product == null) {
            LOG.error("Product not found with ID: {}", productId);
            throw new RecordNotFoundException("Product not found.");
        }
        LOG.info("Returning Product: {}", product);
        return productToProductVOMapper.map(product);
    }

    @Transactional
    @Override
    public ProductVO updateProduct(String productId, ProductVO productVO) {
        Product oldProduct = productRepository.findOne(productId);
        if (oldProduct == null) {
            LOG.error("Product not found with ID: {}", productId);
            throw new RecordNotFoundException("Fatal: Cannot update product that doesn't exist.");
        }
        productVO.setId(productId);
        Product product = productRepository.save(productVOToProductMapper.map(productVO));

        LOG.info("ID: {}, Updated product: {}", productId, product);
        return productToProductVOMapper.map(product);
    }

    @Transactional
    @Override
    public ProductVO deleteProduct(String productId) {
        Product product = productRepository.findOne(productId);
        if (product == null) {
            LOG.error("Product not found with ID: {}", productId);
            throw new RecordNotFoundException("Fatal: Cannot delete product that doesn't exist.");
        }
        productRepository.delete(product);

        LOG.info("ID: {}, Deleted product: {}", productId, product);
        return productToProductVOMapper.map(product);
    }

    @Override
    public PricingDetailsVO getPrices(String productId) {
        Product product = productRepository.findOne(productId);
        if (product == null) {
            LOG.error("Product not found with ID: {}", productId);
            throw new RecordNotFoundException("Product doesn't exist.");
        }

        PricingDetails pricingDetails = pricingDetailsRepository
                .findLatestByProductId(productId);

        LOG.info("Latest PricingDetails: {} for product: {}", pricingDetails, productId);

        PricingDetailsVO pricingDetailsVO = new PricingDetailsVO();
        pricingDetailsVO.setProduct(productId);
        pricingDetailsVO.setName(product.getProductName());
        pricingDetailsVO.setDescription(product.getDescription());
        pricingDetailsVO.setBasePrice(product.getBasePrice());

        if (pricingDetails != null) {
            pricingDetailsVO.setIdealPrice(pricingDetails.getIdealPrice());
            pricingDetailsVO.setAveragePrice(pricingDetails.getAveragePrice());
            pricingDetailsVO.setLowestPrice(pricingDetails.getLowestPrice());
            pricingDetailsVO.setHighestPrice(pricingDetails.getHighestPrice());
        } else {
            LOG.info("Pricing info not available for product with ID: {}", productId);
        }

        LOG.info("Pricing Details response: {}", pricingDetailsVO);
        return pricingDetailsVO;
    }


    @Autowired
    private PricingDetailsRepository pricingDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductToProductVOMapper productToProductVOMapper;

    @Autowired
    private ProductVOToProductMapper productVOToProductMapper;
}
