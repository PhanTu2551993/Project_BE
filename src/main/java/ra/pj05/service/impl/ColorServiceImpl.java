package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.pj05.model.entity.Color;
import ra.pj05.repository.IColorRepository;
import ra.pj05.service.IColorService;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements IColorService {
    @Autowired
    private IColorRepository colorRepository;
    @Override
    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public Color addColor(String colorName) {
        Color color = new Color();
        color.setColorName(colorName);
        return colorRepository.save(color);
    }

    @Override
    public Color saveColor(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public Color findColorById(Long colorId) {
        Optional<Color> color = colorRepository.findById(colorId);
        return color.orElse(null);
    }

    @Override
    public void deleteColorById(Long colorId) {
        colorRepository.deleteById(colorId);
    }
}
